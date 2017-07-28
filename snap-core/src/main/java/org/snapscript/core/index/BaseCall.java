package org.snapscript.core.index;

import java.lang.reflect.Method;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.define.Instance;

public class BaseCall implements MethodCall<Instance> {

   private final Method method;
   
   public BaseCall(Method method) {
      this.method = method;
   }
   
   @Override
   public Object call(Instance instance, Object[] arguments) throws Exception {
      Object value = instance.getObject();
      
      if(value == null) {
         throw new InternalStateException("No 'super' object could be found");
      }
      return method.invoke(value, arguments);
   }
}
