package org.snapscript.tree.dispatch;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.tree.NameReference;

public class ObjectDispatcher implements InvocationDispatcher<Object> {
   
   private final NameReference reference;      
   
   public ObjectDispatcher(NameReference reference) {
      this.reference = reference;
   }

   @Override
   public Value dispatch(Scope scope, Object object, Object... arguments) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      String name = reference.getName(scope);
      Callable<Value> call = binder.bind(scope, object, name, arguments);
      
      if(call == null) {
         ErrorHandler handler = context.getHandler();
         handler.throwInternalException(scope, object, name, arguments);
      }
      return call.call();
   }
}