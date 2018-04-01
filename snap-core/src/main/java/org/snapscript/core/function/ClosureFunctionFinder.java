package org.snapscript.core.function;

import static org.snapscript.core.Reserved.METHOD_EQUALS;
import static org.snapscript.core.Reserved.METHOD_HASH_CODE;
import static org.snapscript.core.Reserved.METHOD_TO_STRING;

import java.util.List;
import java.util.Set;

import org.snapscript.core.ModifierType;
import org.snapscript.core.type.Type;
import org.snapscript.core.convert.FunctionComparator;
import org.snapscript.core.convert.Score;
import org.snapscript.core.type.Category;
import org.snapscript.core.type.TypeCache;
import org.snapscript.core.type.TypeExtractor;
import org.snapscript.core.type.TypeLoader;

public class ClosureFunctionFinder {

   private final TypeCache<Function> functions;
   private final FunctionComparator comparator;
   private final TypeExtractor extractor;
   private final TypeLoader loader;
   private final Signature signature;
   private final Function invalid;
   
   public ClosureFunctionFinder(FunctionComparator comparator, TypeExtractor extractor, TypeLoader loader) {
      this.functions = new TypeCache<Function>();
      this.signature = new EmptySignature();
      this.invalid = new EmptyFunction(signature);
      this.comparator = comparator;
      this.extractor = extractor;
      this.loader = loader;
   }
   
   public Function findFunctional(Object actual) throws Exception {
      if(actual != null) {
         Class type = actual.getClass();
         
         if(type == Class.class) {
            return findFunctional((Class)actual);
         }
         return findFunctional((Type)actual);
      }
      return null;
   }
   
   public Function findFunctional(Class actual) throws Exception {
      if(actual.isInterface()) { 
         Type type = loader.loadType(actual);
         
         if(type != null) {
            return findFunctional(type);
         }
      }
      return null;
   }

   public Function findFunctional(Type type) throws Exception {
      Function function = findMatch(type);
      Signature signature = function.getSignature();
      
      if(!signature.isInvalid()) {
         return function;
      }
      return null;
   }

   private Function findMatch(Type type) throws Exception {
      Function function = functions.fetch(type);
      
      if(function == null) {
         Function match = resolveBest(type);
         
         if(match != null) {
            functions.cache(type, match);
         } else {
            functions.cache(type, invalid);
         }
         return match;
      }
      return function;
   }
 
   private Function resolveBest(Type type) throws Exception {
      Category category = type.getCategory();
      
      if(category.isFunction()) {
         List<Function> functions = type.getFunctions();
         
         if(!functions.isEmpty()) {
            return functions.get(0);
         }
      }
      return resolveSingle(type);
   }
   
   private Function resolveSingle(Type type) throws Exception {
      Set<Type> types = extractor.getTypes(type);
      Function function = invalid;
      
      for(Type base : types){
         Function match = resolveSingleAbstract(base);
         
         if(match != null) {
            if(function != invalid) {
               Score score = comparator.compare(match, function);
               
               if(score.isExact()) {
                  return null;
               }
            }
            function = match;
         }
      } 
      return function;
   }
   
   private Function resolveSingleAbstract(Type type) throws Exception {
      List<Function> functions = type.getFunctions();
      Function match = null;
      int count = 0;
      
      for(Function function : functions) {
         int modifiers = function.getModifiers();
         
         if(ModifierType.isAbstract(modifiers)) {
            if(isValid(function)) {
               match = function;
               count++;
            }
         }
      }  
      if(count > 1) {
         return invalid;
      }
      if(count == 1) {
         return match;
      }
      return null;
   }
   
   private boolean isValid(Function function) throws Exception {
      String name = function.getName();
      Signature signature = function.getSignature();
      List<Parameter> parameters = signature.getParameters();
      int width = parameters.size();
      
      if(name.equals(METHOD_HASH_CODE)) {
         return width != 0;
      } 
      if(name.equals(METHOD_EQUALS)) {
         return width != 1;
      } 
      if(name.equals(METHOD_TO_STRING)) {
         return width != 0;
      }
      return true;
   }
         
}