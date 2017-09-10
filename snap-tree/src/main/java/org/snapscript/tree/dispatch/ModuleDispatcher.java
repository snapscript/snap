package org.snapscript.tree.dispatch;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.error.ErrorHandler;

public class ModuleDispatcher implements InvocationDispatcher {
   
   private final Module module;  
   private final Object object;
   private final Scope scope;
   
   public ModuleDispatcher(Scope scope, Object object) {
      this.module = (Module)object;
      this.object = object;
      this.scope = scope;
   }

   @Override
   public Value dispatch(String name, Object... arguments) throws Exception {   
      Callable<Value> call = bind(name, arguments);
      
      if(call == null) {
         Context context = module.getContext();
         ErrorHandler handler = context.getHandler();
         
         handler.throwInternalException(scope, module, name, arguments);
      }
      return call.call();           
   }
   
   private Callable<Value> bind(String name, Object... arguments) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();    
      Callable<Value> call = binder.bind(scope, module, name, arguments);
      
      if(call == null) {
         return binder.bind(scope, object, name, arguments);
      }
      return call;
   }
}