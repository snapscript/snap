package org.snapscript.core;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;

public class ValueTypeExtractor {

   private final ValueTypeConverter converter;
   private final Cache<Class, Type> types;
   
   public ValueTypeExtractor() {
      this.types = new CopyOnWriteCache<Class, Type>();
      this.converter = new ValueTypeConverter();
   }
   
   public Type extract(Scope scope, Object left) throws Exception {
      if(left != null) {
         Class type = left.getClass();
         Type match = types.fetch(type);
         
         if(match == null) {
            match = converter.convert(left);
         }
         if(match == null) {
            Module module = scope.getModule();
            Type actual = module.getType(type);
            
            if(actual != null) {
               types.cache(type, actual);
            }
            return actual;
         }
         return match;
      }
      return null;
   }
}
