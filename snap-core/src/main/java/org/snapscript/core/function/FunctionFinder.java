package org.snapscript.core.function;

import static org.snapscript.core.Reserved.METHOD_EQUALS;
import static org.snapscript.core.Reserved.METHOD_HASH_CODE;
import static org.snapscript.core.Reserved.METHOD_TO_STRING;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.TypeLoader;

public class FunctionFinder {
   
   private final Cache<Class, Function> functions;
   private final Set<Class> failures;
   private final TypeExtractor extractor;
   private final TypeLoader loader;
   
   public FunctionFinder(TypeExtractor extractor, TypeLoader loader) {
      this.functions = new CopyOnWriteCache<Class, Function>();
      this.failures = new CopyOnWriteArraySet<Class>();
      this.extractor = extractor;
      this.loader = loader;
   }
   
   public Function find(Class actual) throws Exception {
      if(actual.isInterface()) { 
         if(failures.contains(actual)) {
            return null;
         }
         Function function = functions.fetch(actual);
         
         if(function == null) {
            Type type = loader.loadType(actual);
            Function match = find(type);
            
            if(match != null) {
               functions.cache(actual, match);
               return match;
            }
            failures.add(actual);
         }
         return function;
      }
      return null;
   }

   public Function find(Type type) throws Exception {
      Set<Type> types = extractor.getTypes(type);
      
      for(Type base : types){
         List<Function> functions = base.getFunctions();
         Function match = null;
         int count = 0;
         
         for(Function function : functions) {
            int modifiers = function.getModifiers();
            
            if(ModifierType.isAbstract(modifiers)) {
               if(match(function)) {
                  match = function;
                  count++;
               }
            }
         }        
         if(count == 1) {
            return match;
         }
      } 
      return null;
   }
   
   private boolean match(Function function) throws Exception {
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