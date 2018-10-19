package org.snapscript.core.function.dispatch;

import static org.snapscript.core.error.Reason.INVOKE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.proxy.Delegate;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class DelegateDispatcher implements FunctionDispatcher {
   
   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;      
   
   public DelegateDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.resolver = resolver;
      this.handler = handler;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception {
      Type object = constraint.getType(scope);
      FunctionCall call = resolver.resolveFunction(scope, object, name, arguments);
      
      if(call == null) {
         handler.handleCompileError(INVOKE, scope, object, name, arguments);
      }
      return call.check(constraint, arguments);
   }
   
   @Override
   public Call2 dispatch(Scope scope, Value value, Object... arguments) throws Exception {
      Delegate object = value.getValue();
      FunctionCall call = resolver.resolveFunction(scope, object, name, arguments);
      
      if(call == null) {
         handler.handleRuntimeError(INVOKE, scope, object, name, arguments);
      }
      return new Call2(call) {
         
         public Object invoke(Scope scope, Object source, Object... arguments) throws Exception{
            source = ((Value)source).getValue();
            return call.invoke(scope, source, arguments);
         }
      };
   }
}