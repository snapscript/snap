package org.snapscript.tree.dispatch;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.tree.NameReference;

public class ModuleDispatcher implements InvocationDispatcher<Module> {
   
   private final NameReference reference;
   
   public ModuleDispatcher(NameReference reference) {
      this.reference = reference;
   }

   @Override
   public Value dispatch(Scope scope, Module module, Object... arguments) throws Exception {   
      Callable<Value> call = bind(scope, module, arguments);
      
      if(call == null) {
         Context context = module.getContext();
         ErrorHandler handler = context.getHandler();
         String name = reference.getName(scope);
         
         handler.throwInternalException(scope, module, name, arguments);
      }
      return call.call();           
   }
   
   private Callable<Value> bind(Scope scope, Module module, Object... arguments) throws Exception {
      Scope inner = module.getScope();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();  
      String name = reference.getName(scope);
      Callable<Value> call = binder.bind(inner, module, name, arguments);
      
      if(call == null) {
         return binder.bind(inner, (Object)module, name, arguments);
      }
      return call;
   }
}