package org.snapscript.core.dispatch;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.error.ErrorHandler;

public class ScopeDispatcher implements CallDispatcher<Scope> {
   
   private final FunctionBinder binder;
   private final ErrorHandler handler;
   private final String name;
   
   public ScopeDispatcher(FunctionBinder binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }

   @Override
   public Value dispatch(Scope scope, Scope object, Object... arguments) throws Exception {
      Callable<Value> match = bind(scope, object, arguments);
      
      if(match == null) {
         Type type = object.getType();
         
         if(type != null) {
            handler.throwInternalException(scope, type, name, arguments);
         }
         handler.throwInternalException(scope, name, arguments);
      }
      return match.call();          
   }
   
   private Callable<Value> bind(Scope scope, Scope object, Object... arguments) throws Exception {
      Callable<Value> local = binder.bind(scope, object, name, arguments);
      
      if(local == null) {
         Module module = scope.getModule();
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