package org.snapscript.core.index;

import static org.snapscript.core.Reserved.TYPE_SUPER;

import java.lang.reflect.Method;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.define.Instance;

public class BaseCall implements MethodCall<Instance> {

   private final Method method;
   
   public BaseCall(Method method) {
      this.method = method;
   }
   
   @Override
   public Object call(Instance instance, Object[] arguments) throws Exception {
      State state = instance.getState();
      Value value = state.get(TYPE_SUPER);
      
      if(value == null) {
         throw new InternalStateException("No 'super' object could be found");
      }
      Object real = value.getValue();
      Type type = instance.getType();
      
      if(real == null) {
         throw new InternalStateException("The 'super' object for '" + type + "' was null");
      }
      return method.invoke(real, arguments);
   }
}
