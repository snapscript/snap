package org.snapscript.core.function.dispatch;

import static org.snapscript.core.error.Reason.INVOKE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.Connection;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionConnection;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class TypeInstanceDispatcher implements FunctionDispatcher {
   
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
      return call.check(scope, constraint, arguments);
   }
   
   @Override
   public Connection dispatch(Scope scope, Value value, Object... arguments) throws Exception {
      Object object = value.getValue();
      FunctionCall call = resolver.resolveInstance(scope, object, name, arguments);
      
      if(call == null) {
         handler.handleRuntimeError(INVOKE, scope, object, name, arguments);
      }
      return new FunctionConnection(call);
   }
}