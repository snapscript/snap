package org.snapscript.compile.validate;

import java.util.List;
import java.util.Set;

import org.snapscript.core.ModifierType;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.FunctionComparator;
import org.snapscript.core.convert.Score;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Origin;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.index.FunctionIndexer;
import org.snapscript.core.function.index.FunctionPointer;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;

public class FunctionValidator {
   
   private final FunctionComparator comparator;
   private final FunctionIndexer indexer;
   private final TypeExtractor extractor;
   
   public FunctionValidator(ConstraintMatcher matcher, TypeExtractor extractor, FunctionIndexer indexer) {
      this.comparator = new FunctionComparator(matcher);
      this.extractor = extractor;
      this.indexer = indexer;
   }
   
   public void validate(Function function) throws Exception {
      Type source = function.getSource();
      
      if(source == null) {
         throw new ValidateException("Function '" + function + "' does not have a declaring type");
      }
      validateModifiers(function);
      validateDuplicates(function);
   }
   
   public void validate(Function function, Type type) throws Exception {
      Type source = function.getSource();
      
      if(source == type) {
         throw new ValidateException("Function '" + function + "' is abstract but '" + type + "' is not");
      }
      if(source == null) {
         throw new ValidateException("Function '" + function + "' does not have a declaring type");
      }
      validateImplemented(function, type);      
   }
   
   private void validateImplemented(Function function, Type type) throws Exception {
      Scope scope = type.getScope();
      int modifiers = function.getModifiers();
      
      if(ModifierType.isAbstract(modifiers)) { 
         Signature signature = function.getSignature();                       
         List<Parameter> parameters = signature.getParameters();
         Origin origin = signature.getOrigin();
         String name = function.getName();            
         int length = parameters.size();
         
         if(!origin.isSystem()) {
            Type[] types = new Type[length];
            
            for(int i = 0; i < length; i++){
               Parameter parameter = parameters.get(i);
               Constraint constraint = parameter.getConstraint();
               Type match = constraint.getType(scope);
               
               types[i] = match;
            }
            FunctionPointer resolved = indexer.index(type, name, types);
            
            if(resolved == null) {
               throw new ValidateException("Type '" + type + "' must implement '" + function + "'");
            }
            Function match = resolved.getFunction();
            int mask = match.getModifiers();
            
            if(ModifierType.isAbstract(mask)) {
               throw new ValidateException("Type '" + type + "' must implement '" + function + "'");
            }
         }
      }
   }
   
   private void validateModifiers(Function function) throws Exception {
      Type source = function.getSource();
      Scope scope = source.getScope();
      int modifiers = function.getModifiers();
      
      if(ModifierType.isOverride(modifiers)) {
         Set<Type> types = extractor.getTypes(source);
         String name = function.getName();
         int matches = 0;
         
         for(Type type : types) {
            if(type != source) {
               List<Function> functions = type.getFunctions();
               
               for(Function available : functions) {
                  String match = available.getName();
                  
                  if(name.equals(match)) {
                     Score compare = comparator.compare(scope, available, function);
                     
                     if(compare.isSimilar()) {
                        validateModifiers(available, function);
                        matches++;
                     }
                  }
               }
            }
         }
         if(matches == 0) {
            throw new ValidateException("Function '" + function + "' is not an override");
         }
      }
   }
   
   private void validateModifiers(Function actual, Function require) throws Exception {
      Signature signature = actual.getSignature();
      List<Parameter> parameters = signature.getParameters();
      Origin origin = signature.getOrigin();
      Type source = require.getSource();
      Scope scope = source.getScope();
      String name = actual.getName();
      int length = parameters.size();
      
      if(!origin.isSystem()) {
         Type[] types = new Type[length];
         
         for(int i = 0; i < length; i++){
            Parameter parameter = parameters.get(i);
            Constraint constraint = parameter.getConstraint();
            Type type = constraint.getType(scope);
            
            types[i] = type;
         }
         FunctionPointer match = indexer.index(source, name, types);
         
         if(match == null) {
            throw new ValidateException("Function '" + require +"' does not match override");
         }
         Function function = match.getFunction();
         
         if(function != require) {
            throw new ValidateException("Function '" + require +"' does not match override");
         }
      }
   }

   private void validateDuplicates(Function actual) throws Exception {
      Type source = actual.getSource();
      int modifiers = actual.getModifiers();
      
      if(!ModifierType.isAbstract(modifiers)) {
         Signature signature = actual.getSignature();
         List<Parameter> parameters = signature.getParameters();
         Origin origin = signature.getOrigin();
         Scope scope = source.getScope();
         String name = actual.getName();
         int length = parameters.size();
         
         if(!origin.isSystem()) {
            Type[] types = new Type[length];
            
            for(int i = 0; i < length; i++){
               Parameter parameter = parameters.get(i);
               Constraint constraint = parameter.getConstraint();
               Type type = constraint.getType(scope);
               
               types[i] = type;
            }
            FunctionPointer resolved = indexer.index(source, name, types);
            
            if(resolved == actual) {
               throw new ValidateException("Function '" + actual +"' has a duplicate '" + resolved + "'");
            }
            Function function = resolved.getFunction();
            
            if(function != actual) {
               throw new ValidateException("Function '" + actual +"' has a duplicate '" + resolved + "'");
            }
         }
      }
   }
}