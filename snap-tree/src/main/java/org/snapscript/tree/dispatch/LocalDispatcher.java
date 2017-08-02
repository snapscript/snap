package org.snapscript.tree.dispatch;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.bind.FunctionBinder;

public class LocalDispatcher implements InvocationDispatcher {
   
   private final Scope scope;      
   
   public LocalDispatcher(Scope scope) {
      this.scope = scope;
   }

   @Override
   public Value dispatch(String name, Object... arguments) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      Callable<Result> local = binder.bind(scope, module, name, arguments);
      
      if(local == null) {
         Callable<Result> closure = binder.bind(scope, name, arguments); // function variable
         
         if(closure != null) {
            Result result = closure.call();
            Object data = result.getValue();
            
            return ValueType.getTransient(data);   
         }
      }
      if(local == null) {
         throw new InternalStateException("Method '" + name + "' not found in scope");
      }
      Result result = local.call();
      Object value = result.getValue();
      
      return ValueType.getTransient(value);  
   }
   
}