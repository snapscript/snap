package org.snapscript.core.index;

import java.lang.reflect.Method;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Type;
import org.snapscript.core.bridge.BridgeProvider;
import org.snapscript.core.bridge.BridgeBuilder;
import org.snapscript.core.define.SuperInstance;
import org.snapscript.core.function.Invocation;

public class SuperCall implements MethodCall<SuperInstance> {
   
   private final Method method;
   
   public SuperCall(Method method) {
      this.method = method;
   }

   @Override
   public Object call(SuperInstance instance, Object[] arguments) throws Exception {
      String name = method.getName();
      Type type = instance.getType();
      Object value = instance.getBridge();
      
      if(value == null) {
         throw new InternalStateException("No 'super' object could be found");
      }
      Module module = instance.getModule();
      Context context = module.getContext();
      BridgeProvider provider = context.getProvider();
      BridgeBuilder extender = provider.create(type);

      if(extender == null) {
         throw new InternalStateException("No 'super' method for '" + name + "' found in '" + type + "'");
      }
      Class base = value.getClass();
      Invocation invocation = extender.createInvocation(instance, base, method);
      Result result = invocation.invoke(instance, value, arguments);
      
      return result.getValue();
   }
}
