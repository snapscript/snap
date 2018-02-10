package org.snapscript.tree.define;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.dispatch.CallDispatcher;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.platform.Platform;
import org.snapscript.core.platform.PlatformProvider;

public class SuperDispatcher implements CallDispatcher<Object> {

   private final Type type;
   
   public SuperDispatcher(Type type) {
      this.type = type;
   }
   
   @Override
   public Value validate(Scope scope, Object object, Object... list) throws Exception {
      return Value.getTransient(new Object());
   }
   
   @Override
   public Value dispatch(Scope scope, Object object, Object... list) throws Exception {
      Type real = (Type)list[0];
      Module module = scope.getModule();
      Context context = module.getContext();
      Class base = type.getType();
      
      if(base == null) {
         throw new InternalStateException("Base type of '" + type + "' is null");
      }
      Object[] copy = new Object[list.length - 1];
      
      if(copy.length > 0) {
         System.arraycopy(list, 1, copy, 0, copy.length);
      }
      PlatformProvider provider = context.getProvider();
      Platform platform = provider.create();
      Invocation invocation = platform.createSuperConstructor(real, type);
      Object instance = invocation.invoke(scope, real, copy);
      
      return Value.getTransient(instance, type);
   }
}