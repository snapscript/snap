
package org.snapscript.core.index;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.snapscript.core.InternalStateException;

public class MethodCall {

   private final Method method;
   
   public MethodCall(Method method) {
      this.method = method;
   }
   
   public Object call(Object object, Object[] arguments) throws Exception {
      String name = method.getName();
      
      try {
         return method.invoke(object, arguments);
      }catch(InvocationTargetException cause) {
         Throwable target = cause.getTargetException();
         
         if(target != null) {
            throw new InternalStateException("Error occured invoking '" + name + "'", target);
         }
         throw cause;
      }
   }
}
