package org.snapscript.core.index;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;

import org.snapscript.core.InternalStateException;

public class DefaultMethodHandle {
   
   private final MethodHandleBinder binder;
   private final Method method;

   public DefaultMethodHandle(Method method) {
      this.binder = new MethodHandleBinder(method);
      this.method = method;
   }

   public Object invoke(Object left, Object... arguments) throws Exception {
      MethodHandle handle = binder.bind(left);
      
      try {
         return handle.invokeWithArguments(arguments);
      } catch(Throwable e) {
         throw new InternalStateException("Error invoking default method " + method, e);
      }
   }
}
