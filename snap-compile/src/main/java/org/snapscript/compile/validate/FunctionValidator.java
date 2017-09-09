package org.snapscript.compile.validate;

import java.util.List;
import java.util.Set;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.bind.FunctionResolver;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.FunctionComparator;
import org.snapscript.core.convert.Score;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.ModifierValidator;

public class FunctionValidator {
   
   private final FunctionComparator comparator;
   private final ModifierValidator validator;
   private final FunctionResolver resolver;
   private final TypeExtractor extractor;
   
   public FunctionValidator(ConstraintMatcher matcher, TypeExtractor extractor, FunctionResolver resolver) {
      this.comparator = new FunctionComparator(matcher);
      this.validator = new ModifierValidator();
      this.extractor = extractor;
      this.resolver = resolver;
   }
   
   public void validate(Function function) throws Exception {
      Type type = function.getType();
      
      if(type == null) {
         throw new InternalStateException("Function '" + function + "' does not have a type");
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
                     
                     if(compare.isValid()) {
                        validateModifiers(available, function);
                        matches++;
                     }
                  }
               }
            }
         }
         if(matches == 0) {
            throw new InternalStateException("Function '" + function + "' is not an override");
         }
      }
      validator.validate(parent, function, modifiers);
   }
   
   private void validateModifiers(Function actual, Function require) throws Exception {
      Signature signature = actual.getSignature();
      List<Parameter> parameters = signature.getParameters();
      Type parent = require.getType();
      String name = actual.getName();
      int length = parameters.size();
      
      if(length >0) {
         Type[] types = new Type[length];
         
         for(int i = 0; i < length; i++){
            Parameter parameter = parameters.get(i);
            Type type = parameter.getType();
            
            types[i] = type;
         }
         Function match = resolver.resolve(parent, name, types);
         
         if(match != require) {
            throw new IllegalStateException("Function '" + require +"' does not match override");
         }
      }
   }
   
   private void validateDuplicates(Function function) throws Exception {
      Type parent = function.getType();
      int modifiers = function.getModifiers();
      
      if(!ModifierType.isAbstract(modifiers)) {
         Signature signature = function.getSignature();
         List<Parameter> parameters = signature.getParameters();
         String name = function.getName();
         int length = parameters.size();
         
         if(length >0) {
            Type[] types = new Type[length];
            
            for(int i = 0; i < length; i++){
               Parameter parameter = parameters.get(i);
               Type type = parameter.getType();
               
               types[i] = type;
            }
            Function resolved = resolver.resolve(parent, name, types);
            
            if(resolved != function) {
               throw new IllegalStateException("Function '" + function +"' has a duplicate '" + resolved + "'");
            }
         }
      }
   }
}