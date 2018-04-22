package org.snapscript.core.function.dispatch;

import static org.snapscript.core.error.Reason.INVOKE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class LocalDispatcher implements FunctionDispatcher<Object> {
   
   private final FunctionResolver binder;
   private final ErrorHandler handler;
   private final String name;  
   
   public LocalDispatcher(FunctionResolver binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }

   @Override
   public Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception {
      Type object = constraint.getType(scope);
      FunctionCall call = bind(scope, object, arguments);
      
      if(call == null) {
         handler.handleCompileError(INVOKE, scope, name, arguments);
      }
      return call.check(constraint);
   }
   
   @Override
   public Value dispatch(Scope scope, Object object, Object... arguments) throws Exception {
      FunctionCall call = bind(scope, object, arguments);
      
      if(call == null) {
         handler.handleRuntimeError(INVOKE, scope, name, arguments);
      }
      return call.call();
   }
   
   private FunctionCall bind(Scope scope, Object object, Object... arguments) throws Exception {
      Module module = scope.getModule();
      FunctionCall local = binder.resolveModule(scope, module, name, arguments);
      
      if(local == null) {
         FunctionCall closure = binder.resolveScope(scope, name, arguments); // function variable
         
         if(closure != null) {
            return closure;   
         }
      }
      return local;  
   }
   
   private FunctionCall bind(Scope scope, Type object, Type... arguments) throws Exception {
      Module module = scope.getModule();
      FunctionCall local = binder.resolveModule(scope, module, name, arguments);
      
      if(local == null) {
         FunctionCall closure = binder.resolveScope(scope, name, arguments); // function variable
         
         if(closure != null) {
            return closure;   
         }
      }
      return local;  
   }
   
}