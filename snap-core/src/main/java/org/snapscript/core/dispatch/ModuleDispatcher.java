package org.snapscript.core.dispatch;

import java.util.concurrent.Callable;

import org.snapscript.core.Constraint;
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
   public Constraint compile(Scope scope, Type module, Type... arguments) throws Exception {
      Module mod = module.getModule();
      InvocationTask call = bind(scope, mod, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, module, name, arguments);
      }
      return call.getReturn();    
   }

   @Override
   public Value dispatch(Scope scope, Module module, Object... arguments) throws Exception {   
      Callable<Value> call = bind(scope, module, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, module, name, arguments);
      }
      return call.call();           
   }
   
   private InvocationTask bind(Scope scope, Module module, Type... arguments) throws Exception {
      Scope inner = module.getScope();
      InvocationTask call = binder.bindModule(inner, module, name, arguments);
      
      if(call == null) {
         return binder.bindInstance(inner, (Object)module, name, arguments);
      }
      return call;
   }
   
   private InvocationTask bind(Scope scope, Module module, Object... arguments) throws Exception {
      Scope inner = module.getScope();
      InvocationTask call = binder.bindModule(inner, module, name, arguments);
      
      if(call == null) {
         return binder.bindInstance(inner, (Object)module, name, arguments);
      }
      return call;
   }
}