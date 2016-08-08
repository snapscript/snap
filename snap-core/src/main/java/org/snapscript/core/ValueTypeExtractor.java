package org.snapscript.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ValueTypeExtractor {

   private final ValueTypeConverter converter;
   private final Map<Class, Type> types;
   
   public ValueTypeExtractor() {
      this.types = new ConcurrentHashMap<Class, Type>();
      this.converter = new ValueTypeConverter();
   }
   
   public Type extract(Scope scope, Object left) throws Exception {
      if(left != null) {
         Class type = left.getClass();
         Type match = types.get(type);
         
         if(match == null) {
            match = converter.convert(left);
         }
         if(match == null) {
            Module module = scope.getModule();
            Type actual = module.getType(type);
            
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
