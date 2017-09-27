package org.snapscript.tree.dispatch;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.tree.NameReference;

public class LocalDispatcher implements InvocationDispatcher<Object> {
   
   private final NameReference reference;      
   
   public LocalDispatcher(NameReference reference) {
      this.reference = reference;
   }

   @Override
   public Value dispatch(Scope scope, Object object, Object... arguments) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      String name = reference.getName(scope);
      Callable<Value> local = binder.bind(scope, module, name, arguments);
      
      if(local == null) {
         Callable<Value> closure = binder.bind(scope, name, arguments); // function variable
         
         if(closure != null) {
            return closure.call();   
         }
      }
      if(local == null) {
         ErrorHandler handler = context.getHandler();
         handler.throwInternalException(scope, name, arguments);
      }
      return local.call();  
   }
   
}