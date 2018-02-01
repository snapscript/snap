package org.snapscript.core.dispatch;

import java.util.concurrent.Callable;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.error.ErrorHandler;

public class LocalDispatcher implements CallDispatcher<Object> {
   
   private final FunctionBinder binder;
   private final ErrorHandler handler;
   private final String name;  
   
   public LocalDispatcher(FunctionBinder binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }

   @Override
   public Value dispatch(Scope scope, Object object, Object... arguments) throws Exception {
      Module module = scope.getModule();
      Callable<Value> local = binder.bind(scope, module, name, arguments);
      
      if(local == null) {
         Callable<Value> closure = binder.bind(scope, name, arguments); // function variable
         
         if(closure != null) {
            return closure.call();   
         }
      }
      if(local == null) {
         handler.throwInternalException(scope, name, arguments);
      }
      return local.call();  
   }
   
}