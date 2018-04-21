package org.snapscript.compile.validate;

import java.util.List;
import java.util.Set;

import org.snapscript.core.ModifierType;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.FunctionComparator;
import org.snapscript.core.convert.Score;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.index.FunctionIndexer;
import org.snapscript.core.function.index.FunctionPointer;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;
import org.snapscript.tree.ModifierValidator;

public class FunctionValidator {
   
   private final FunctionComparator comparator;
   private final ModifierValidator validator;
   private final FunctionIndexer indexer;
   private final TypeExtractor extractor;
   
   public FunctionValidator(ConstraintMatcher matcher, TypeExtractor extractor, FunctionIndexer indexer) {
      this.comparator = new FunctionComparator(matcher);
      this.validator = new ModifierValidator();
      this.extractor = extractor;
      this.indexer = indexer;
   }
   
   public void validate(Function function) throws Exception {
      Type type = function.getType();
      
      if(type == null) {
         throw new ValidateException("Function '" + function + "' does not have a type");
      }
      validateModifiers(function);
      validateDuplicates(function);
   }
   
   private void validateModifiers(Function function) throws Exception {
      Type parent = function.getType();
      int modifiers = function.getModifiers();
      
      if(ModifierType.isOverride(modifiers)) {
         Set<Type> types = extractor.getTypes(parent);
         String name = function.getName();
         int matches = 0;
         
         for(Type type : types) {
            if(type != parent) {
               List<Function> functions = type.getFunctions();
               
               for(Function available : functions) {
                  String match = available.getName();
                  
                  if(name.equals(match)) {
                     Score compare = comparator.compare(available, function);
                     
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
      validator.validate(parent, function, modifiers);
   }
   
   private void validateModifiers(Function actual, Function require) throws Exception {
      Signature signature = actual.getSignature();
      List<Parameter> parameters = signature.getParameters();
      Type parent = require.getType();
      Scope scope = parent.getScope();
      String name = actual.getName();
      int length = parameters.size();
      
      if(length >0) {
         Type[] types = new Type[length];
         
         for(int i = 0; i < length; i++){
            Parameter parameter = parameters.get(i);
            Constraint constraint = parameter.getConstraint();
            Type type = constraint.getType(scope);
            
            types[i] = type;
         }
         FunctionPointer match = indexer.index(parent, name, types);
         
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
      Type parent = actual.getType();
      int modifiers = actual.getModifiers();
      
      if(!ModifierType.isAbstract(modifiers)) {
         Signature signature = actual.getSignature();
         List<Parameter> parameters = signature.getParameters();
         Scope scope = parent.getScope();
         String name = actual.getName();
         int length = parameters.size();
         
         if(length >0) {
            Type[] types = new Type[length];
            
            for(int i = 0; i < length; i++){
               Parameter parameter = parameters.get(i);
               Constraint constraint = parameter.getConstraint();
               Type type = constraint.getType(scope);
               
               types[i] = type;
            }
            FunctionPointer resolved = indexer.index(parent, name, types);
            
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