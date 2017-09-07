package org.snapscript.core.index;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.function.Invocation;

public class ObjectCall implements MethodCall {
   
   private final Invocation invocation;
   private final Method method;
   
   public ObjectCall(Invocation invocation, Method method) {
      this.invocation = invocation;
      this.method = method;
   }
   
   @Override
   public Object call(Object object, Object[] arguments) throws Exception {
      try {
         return invocation.invoke(null, object, arguments);
      }catch(InvocationTargetException cause) {
         Throwable target = cause.getTargetException();
         String name = method.getName();
         
         if(target != null) {
            throw new InternalStateException("Error occured invoking '" + name + "'", target);
         }
         throw cause;
      }
   }
}