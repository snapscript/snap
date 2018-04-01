package org.snapscript.core.function.dispatch;

import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.concurrent.Callable;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.search.FunctionCall;
import org.snapscript.core.function.search.FunctionSearcher;

public class ModuleDispatcher implements FunctionDispatcher<Module> {
   
   private final FunctionSearcher binder;
   private final ErrorHandler handler;
   private final String name;
   
   public ModuleDispatcher(FunctionSearcher binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Type type, Type... arguments) throws Exception {
      Module module = type.getModule();
      FunctionCall call = bind(scope, module, arguments);
      
      if(call == null) {
         handler.handleCompileError(scope, type, name, arguments);
         return NONE;
      }
      return call.check();    
   }

   @Override
   public Value dispatch(Scope scope, Module module, Object... arguments) throws Exception {   
      FunctionCall call = bind(scope, module, arguments);
      
      if(call == null) {
         handler.handleRuntimeError(scope, module, name, arguments);
      }
      return call.call();           
   }
   
   private FunctionCall bind(Scope scope, Module module, Type... arguments) throws Exception {
      Scope inner = module.getScope();
      FunctionCall call = binder.searchModule(inner, module, name, arguments);
      
      if(call == null) {
         return binder.searchInstance(inner, (Object)module, name, arguments);
      }
      return call;
   }
   
   private FunctionCall bind(Scope scope, Module module, Object... arguments) throws Exception {
      Scope inner = module.getScope();
      FunctionCall call = binder.searchModule(inner, module, name, arguments);
      
      if(call == null) {
         return binder.searchInstance(inner, (Object)module, name, arguments);
      }
      return call;
   }
}