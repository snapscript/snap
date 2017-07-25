package org.snapscript.core.index;

import static org.snapscript.core.Reserved.TYPE_SUPER;

import java.lang.reflect.Method;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.define.SuperInstance;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.generate.ExtensionProvider;
import org.snapscript.core.generate.TypeExtender;

public class SuperCall implements MethodCall<SuperInstance> {
   
   private final Method method;
   
   public SuperCall(Method method) {
      this.method = method;
   }

   @Override
   public Object call(SuperInstance instance, Object[] arguments) throws Exception {
      State state = instance.getState();
      Value value = state.get(TYPE_SUPER);
      
      if(value == null) {
         throw new InternalStateException("No 'super' object could be found");
      }
      String name = method.getName();
      Type type = instance.getType();
      Object real = value.getValue();
      
      if(real == null) {
         throw new InternalStateException("The 'super' object for '" + type + "' was null");
      }
      Module module = instance.getModule();
      Context context = module.getContext();
      ExtensionProvider provider = context.getProvider();
      TypeExtender extender = provider.create(type);

      if(extender == null) {
         throw new InternalStateException("No 'super' method for '" + name + "' found in '" + type + "'");
      }
      Class base = real.getClass();
      Invocation invocation = extender.createSuper(instance, base, method);
      Result result = invocation.invoke(instance, real, arguments);
      
      return result.getValue();
   }
}
