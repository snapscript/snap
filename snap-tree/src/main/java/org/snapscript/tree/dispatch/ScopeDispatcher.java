package org.snapscript.tree.dispatch;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.tree.NameReference;

public class ScopeDispatcher implements InvocationDispatcher<Scope> {
   
   private final NameReference reference;    
   
   public ScopeDispatcher(NameReference reference) {
      this.reference = reference;
   }

   @Override
   public Value dispatch(Scope scope, Scope object, Object... arguments) throws Exception {
      Callable<Value> match = bind(scope, object, arguments);
      
      if(match == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         ErrorHandler handler = context.getHandler();
         String name = reference.getName(scope);
         Type type = object.getType();
         
         if(type != null) {
            handler.throwInternalException(scope, type, name, arguments);
         }
         handler.throwInternalException(scope, name, arguments);
      }
      return match.call();          
   }
   
   private Callable<Value> bind(Scope scope, Scope object, Object... arguments) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      String name = reference.getName(scope);
      Callable<Value> local = binder.bind(scope, object, name, arguments);
      
      if(local == null) {
         Callable<Value> external = binder.bind(scope, module, name, arguments); // maybe closure should be first
         
         if(external != null) {
            return external;
         }
         Callable<Value> closure = binder.bind(object, name, arguments); // closure
         
         if(closure != null) {
            return closure;
         }
      }
      return local;  
   }
}