package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.CONSTANT;

import org.snapscript.core.Context;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.function.Connection;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.dispatch.FunctionDispatcher;
import org.snapscript.core.module.Module;
import org.snapscript.core.platform.Platform;
import org.snapscript.core.platform.PlatformProvider;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class SuperDispatcher implements FunctionDispatcher {

   private final Type type;
   
   public SuperDispatcher(Type type) {
      this.type = type;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint object, Type... list) throws Exception {
      return Constraint.getConstraint(type, CONSTANT.mask);
   }
   
   @Override
   public Connection dispatch(Scope scope, Value value, Object... list) throws Exception {
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
      
      return new SuperConnection(instance);
   }
   
   private static class SuperConnection implements Connection {
      
      private final Object instance;
      
      public SuperConnection(Object instance) {
         this.instance = instance;
      }

      @Override
      public boolean accept(Scope scope, Object object, Object... arguments) throws Exception {
         return false;
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... list) throws Exception {
         return instance;
      }
   }
}