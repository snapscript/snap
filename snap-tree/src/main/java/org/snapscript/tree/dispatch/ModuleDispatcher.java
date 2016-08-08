package org.snapscript.tree.dispatch;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.ValueTypeExtractor;
import org.snapscript.core.bind.FunctionBinder;

public class ModuleDispatcher implements InvocationDispatcher {
   
   private final ObjectDispatcher handler;
   private final Module module;   
   
   public ModuleDispatcher(ValueTypeExtractor extractor, Scope scope, Object object) {
      this.handler = new ObjectDispatcher(extractor, scope, object);
      this.module = (Module)object;
   }

   @Override
   public Value dispatch(String name, Object... arguments) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();    
      Callable<Result> call = binder.bind(scope, module, name, arguments);
      
      if(call == null) {
         return handler.dispatch(name, arguments);
      }
      Result result = call.call();
      Object data = result.getValue();

      return ValueType.getTransient(data);           
   }
}