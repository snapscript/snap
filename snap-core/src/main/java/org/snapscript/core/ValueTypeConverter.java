package org.snapscript.core;

import org.snapscript.core.define.SuperInstance;

public class ValueTypeConverter {
   
   public ValueTypeConverter() {
      super();
   }

   public Type convert(Object left) throws Exception {
      Class type = left.getClass();
      
      if(!Type.class.isInstance(left)) {
         if(SuperInstance.class.isAssignableFrom(type)) {
            SuperInstance reference = (SuperInstance)left;
            return reference.getSuper();
         } 
         if(Scope.class.isAssignableFrom(type)) {
            Scope reference = (Scope)left;
            return reference.getType();
         } 
         return null;
      }
      return (Type)left;
   }
}
