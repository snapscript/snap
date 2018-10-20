package org.snapscript.core.function.dispatch;

import static org.snapscript.core.constraint.Constraint.NONE;
import static org.snapscript.core.error.Reason.INVOKE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.Connection;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class ValueDispatcher implements FunctionDispatcher {
   
   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;
   
   public ValueDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.resolver = resolver;
      this.handler = handler;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint value, Type... list) throws Exception {
      return NONE;
   }

   @Override
   public Connection dispatch(Scope scope, Value value, Object... list) throws Exception {
      Value reference = value.getValue();
      FunctionCall call = resolver.resolveValue(reference, list); // function variable
      
      if(call == null) {
         handler.handleRuntimeError(INVOKE, scope, name, list);
      }
      return new ValueConnection(call);  
   }
   
   private static class ValueConnection implements Connection {

      private final FunctionCall call;
      
      public ValueConnection(FunctionCall call) {
         this.call = call;
      }
      
      @Override
      public boolean match(Scope scope, Object object, Object... arguments) throws Exception {
         return call.match(scope, object, arguments);
      }
      
      @Override
      public Object invoke(Scope scope, Object object, Object... arguments) throws Exception {
         return call.invoke(scope, null, arguments);
      } 
   }
}