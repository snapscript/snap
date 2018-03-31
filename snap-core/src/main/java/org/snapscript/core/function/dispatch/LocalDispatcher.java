package org.snapscript.core.function.dispatch;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.find.FunctionCall;
import org.snapscript.core.function.find.FunctionFinder;

public class LocalDispatcher implements FunctionDispatcher<Object> {
   
   private final FunctionFinder binder;
   private final ErrorHandler handler;
   private final String name;  
   
   public LocalDispatcher(FunctionFinder binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }

   @Override
   public Constraint compile(Scope scope, Type object, Type... arguments) throws Exception {
      FunctionCall call = bind(scope, object, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, name, arguments);
      }
      return call.check();
   }
   
   @Override
   public Value dispatch(Scope scope, Object object, Object... arguments) throws Exception {
      FunctionCall call = bind(scope, object, arguments);
      
      if(call == null) {
         handler.throwInternalException(scope, name, arguments);
      }
      return call.call();
   }
   
   private FunctionCall bind(Scope scope, Object object, Object... arguments) throws Exception {
      Module module = scope.getModule();
      FunctionCall local = binder.bindModule(scope, module, name, arguments);
      
      if(local == null) {
         FunctionCall closure = binder.bindScope(scope, name, arguments); // function variable
         
         if(closure != null) {
            return closure;   
         }
      }
      if(local == null) {
         handler.throwInternalException(scope, name, arguments);
      }
      return local;  
   }
   
   private FunctionCall bind(Scope scope, Type object, Type... arguments) throws Exception {
      Module module = scope.getModule();
      FunctionCall local = binder.bindModule(scope, module, name, arguments);
      
      if(local == null) {
         FunctionCall closure = binder.bindScope(scope, name, arguments); // function variable
         
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