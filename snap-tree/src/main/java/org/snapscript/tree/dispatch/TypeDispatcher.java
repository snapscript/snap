package org.snapscript.tree.dispatch;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.error.ErrorHandler;

public class TypeDispatcher implements InvocationDispatcher {
   
   private final Object object;
   private final Scope scope;      
   private final Type type;
   
   public TypeDispatcher(Scope scope, Object object) {
      this.type = (Type)object;
      this.object = object;
      this.scope = scope;
   }

   @Override
   public Value dispatch(String name, Object... arguments) throws Exception {   
      Callable<Value> call = bind(name, arguments);
      
      if(call == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         ErrorHandler handler = context.getHandler();
         
         handler.throwInternalException(scope, type, name, arguments);
      }
      return call.call();          
   } 
   
   private Callable<Value> bind(String name, Object... arguments) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();    
      Callable<Value> call = binder.bind(scope, type, name, arguments);
      
      if(call == null) {
         return binder.bind(scope, object, name, arguments);
      }
      return call;
   }
}