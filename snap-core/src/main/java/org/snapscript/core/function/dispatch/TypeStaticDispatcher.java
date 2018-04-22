package org.snapscript.core.function.dispatch;

import static org.snapscript.core.error.Reason.INVOKE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class TypeStaticDispatcher implements FunctionDispatcher<Type> {
   
   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;
   
   public TypeStaticDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.resolver = resolver;
      this.handler = handler;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception {
      Type type = constraint.getType(scope);
      FunctionCall call = resolver.resolveStatic(scope, type, name, arguments);

      if(call == null) {
         handler.handleCompileError(INVOKE, scope, type, name, arguments);
      }
      return call.check(constraint);
   } 

   @Override
   public Value dispatch(Scope scope, Type type, Object... arguments) throws Exception {   
      FunctionCall call = resolver.resolveStatic(scope, type, name, arguments);

      if(call == null) {
         call = resolver.resolveInstance(scope, (Object)type, name, arguments); // find on the type
      }
      if(call == null) {
         handler.handleRuntimeError(INVOKE, scope, type, name, arguments);
      }
      return call.call();          
   } 
}