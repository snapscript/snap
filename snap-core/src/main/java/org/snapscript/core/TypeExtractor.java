package org.snapscript.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.snapscript.core.define.SuperInstance;
import org.snapscript.core.function.Function;

public class TypeExtractor {
   
   private final Map<Class, Type> types;
   private final TypeLoader loader;
   
   public TypeExtractor(TypeLoader loader) {
      this.types = new ConcurrentHashMap<Class, Type>();
      this.loader = loader;
   }
   
   public Type extract(Object value) throws Exception {
      if(value != null) {
         Class type = value.getClass();
         Type match = types.get(type);
         
         if(match == null) {
            if(SuperInstance.class.isAssignableFrom(type)) {
               SuperInstance scope = (SuperInstance)value;
               return scope.getSuper();
            } 
            if(Scope.class.isAssignableFrom(type)) {
               Scope scope = (Scope)value;
               return scope.getType();
            } 
            if(Function.class.isAssignableFrom(type)) {
               Function function = (Function)value;
               return function.getDefinition(); // used as an adapter;
            }             
            Type actual = loader.loadType(type);
            
            if(actual != null) {
               types.put(type, actual);
            }
            return actual;
         }
         return match;
      }
      return null;
   }
}
