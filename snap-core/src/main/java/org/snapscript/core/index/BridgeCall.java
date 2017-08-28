package org.snapscript.core.index;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Result;
import org.snapscript.core.define.Instance;
import org.snapscript.core.function.Invocation;

public class BridgeCall implements MethodCall<Instance> {

   private final Invocation invocation;
   
   public BridgeCall(Invocation invocation) {
      this.invocation = invocation;
   }
   
   @Override
   public Object call(Instance instance, Object[] list) throws Exception {
      Object value = instance.getBridge();
      
      if(value == null) {
         throw new InternalStateException("No 'super' object could be found");
      }
      Result result = invocation.invoke(instance, value, list);
      return result.getValue();
   }
}