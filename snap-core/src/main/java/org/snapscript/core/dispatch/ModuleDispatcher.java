package org.snapscript.core.dispatch;

import java.util.Map;
import java.util.concurrent.Callable;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.bind.InvocationTask;
import org.snapscript.core.error.ErrorHandler;

public class ModuleDispatcher implements CallDispatcher<Module> {
   
   private final FunctionBinder binder;
   private final ErrorHandler handler;
   private final String name;
   
   public ModuleDispatcher(FunctionBinder binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }
   
   @Override
   public Value validate(Scope scope, Module module, Object... arguments) throws Exception {
      if(module != null) {
         InvocationTask call = bind(scope, module, arguments);
         if(call == null) {
            handler.throwInternalException(scope, module, name, arguments);
         }
         Object o = null;
         Type type = call.getReturn();
         if(type != null) {
            o = scope.getModule().getContext().getProvider().create().createShellConstructor(type).invoke(scope, null, null);
         } else {
            o = new Object();
         }
         return Value.getTransient(o);
      }
      return Value.getTransient(new Object());
   }

   @Override
   public Value dispatch(Scope scope, Module module, Object... arguments) throws Exception {   
      Callable<Value> call = bind(scope, module, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, module, name, arguments);
      }
      return call.call();           
   }
   
   private InvocationTask bind(Scope scope, Module module, Object... arguments) throws Exception {
      Scope inner = module.getScope();
      InvocationTask call = binder.bind(inner, module, name, arguments);
      
      if(call == null) {
         return binder.bind(inner, (Object)module, name, arguments);
      }
      return call;
   }
}