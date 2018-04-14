package org.snapscript.core.function.dispatch;

import static org.snapscript.core.error.Reason.INVOKE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.core.type.Type;

public class TypeStaticDispatcher implements FunctionDispatcher<Type> {
   
   private final FunctionResolver binder;
   private final ErrorHandler handler;
   private final String name;
   
   public TypeStaticDispatcher(FunctionResolver binder, ErrorHandler handler, String name) {
      this.handler = handler;
      this.binder = binder;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Type type, Type... arguments) throws Exception {   
      FunctionCall call = binder.resolveStatic(scope, type, name, arguments);

      if(call == null) {
         call = binder.resolveInstance(scope, type, name, arguments);
      }
      if(call == null) {
         handler.handleCompileError(INVOKE, scope, type, name, arguments);
      }
      return call.check();
   } 

   @Override
   public Value evaluate(Scope scope, Type type, Object... arguments) throws Exception {   
      FunctionCall call = binder.resolveStatic(scope, type, name, arguments);

      if(call == null) {
         call = binder.resolveInstance(scope, type, name, arguments);
      }
      if(call == null) {
         handler.handleRuntimeError(INVOKE, scope, type, name, arguments);
      }
      return call.call();          
   } 
}