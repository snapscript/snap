package org.snapscript.tree.function;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import org.snapscript.core.type.index.ScopeType;

public class FunctionReferenceAligner {
   
   private final String method;
   private final Object value;

   public FunctionReferenceAligner(Object value, String method){
      this.method = method;
      this.value = value;
   }
   
   public Object[] align(Object... list) throws Exception {      
      if(method.equals(TYPE_CONSTRUCTOR)) {
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