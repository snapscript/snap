package org.snapscript.core.dispatch;

import org.snapscript.core.Constraint;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.bind.InvocationTask;
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
   public Constraint validate(Scope scope, Type object, Type... arguments) throws Exception {
      InvocationTask call = bind(scope, object, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, name, arguments);
      }
      return call.getReturn();
   }
   
   @Override
   public Value dispatch(Scope scope, Object object, Object... arguments) throws Exception {
      InvocationTask call = bind(scope, object, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, name, arguments);
      }
      return call.call();
   }
   
   private InvocationTask bind(Scope scope, Object object, Object... arguments) throws Exception {
      Module module = scope.getModule();
      InvocationTask local = binder.bindModule(scope, module, name, arguments);
      
      if(local == null) {
         InvocationTask closure = binder.bindScope(scope, name, arguments); // function variable
         
         if(closure != null) {
            return closure;   
         }
      }
      if(local == null) {
         handler.throwInternalException(scope, name, arguments);
      }
      return local;  
   }
   
   private InvocationTask bind(Scope scope, Object object, Type... arguments) throws Exception {
      Module module = scope.getModule();
      InvocationTask local = binder.bindModule(scope, module, name, arguments);
      
      if(local == null) {
         InvocationTask closure = binder.bindScope(scope, name, arguments); // function variable
         
         if(closure != null) {
            return closure;   
         }
      }
      if(local == null) {
         handler.throwInternalException(scope, name, arguments);
      }
      return local;  
   }
   
}