package org.snapscript.core.function.dispatch;

import static org.snapscript.core.error.Reason.INVOKE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class TypeInstanceDispatcher implements FunctionDispatcher<Object> {
   
   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;    
   
   public TypeInstanceDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.resolver = resolver;
      this.handler = handler;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception {
      Type object = constraint.getType(scope);
      FunctionCall call = resolver.resolveInstance(scope, object, name, arguments);
      
      if(call == null) {
         handler.handleCompileError(INVOKE, scope, object, name, arguments);
      }
      return call.check(constraint);
   }
   
   @Override
   public Value dispatch(Scope scope, Object object, Object... arguments) throws Exception {
      FunctionCall call = resolver.resolveInstance(scope, object, name, arguments);
      
      if(call == null) {
         handler.handleRuntimeError(INVOKE, scope, object, name, arguments);
      }
      return call.call();
   }
}