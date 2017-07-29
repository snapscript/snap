package org.snapscript.tree.define;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.bridge.BridgeBuilder;
import org.snapscript.core.bridge.BridgeProvider;
import org.snapscript.core.define.Instance;
import org.snapscript.tree.dispatch.InvocationDispatcher;

public class SuperDispatcher implements InvocationDispatcher{

   private final Scope scope;
   private final Type type;
   
   public SuperDispatcher(Scope scope, Type type) {
      this.scope = scope;
      this.type = type;
   }

   @Override
   public Value dispatch(String name, Object... list) throws Exception {
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
      BridgeProvider provider = context.getProvider();
      BridgeBuilder builder = provider.create(type);
      Instance instance = builder.superInstance(scope, real, copy);
      
      return ValueType.getTransient(instance, type);
   }
}
