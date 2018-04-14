package org.snapscript.tree.function;

import org.snapscript.core.type.index.ScopeType;

public class FunctionHandleAligner {
   
   private final Object value;
   private final boolean constructor;

   public FunctionHandleAligner(Object value, boolean constructor){
      this.constructor = constructor;
      this.value = value;
   }
   
   public Object[] align(Object... list) throws Exception {      
      if(constructor) {
         if(ScopeType.class.isInstance(value)) { // inject type parameter
            Object[] arguments = new Object[list.length +1];
         
            for(int i = 0; i < list.length; i++) {
               arguments[i + 1] = list[i];
            }
            arguments[0] = value;
            return arguments;
         }
      }
      return list;
   }
}