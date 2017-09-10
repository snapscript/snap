package org.snapscript.tree.dispatch;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.error.ErrorHandler;

public class ObjectDispatcher implements InvocationDispatcher {
   
   private final Object object;
   private final Scope scope;      
   
   public ObjectDispatcher(Scope scope, Object object) {
      this.object = object;
      this.scope = scope;
   }

   @Override
   public Value dispatch(String name, Object... arguments) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      Callable<Value> call = binder.bind(scope, object, name, arguments);
      
      if(call == null) {
         ErrorHandler handler = context.getHandler();
         handler.throwInternalException(scope, object, name, arguments);
      }
      return call.call();
   }
}