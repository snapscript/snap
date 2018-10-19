package org.snapscript.core.function.dispatch;

import static org.snapscript.core.constraint.Constraint.NONE;
import static org.snapscript.core.error.Reason.INVOKE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.dispatch.FunctionDispatcher.Call2;
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
   public Call2 dispatch(Scope scope, Value value, Object... list) throws Exception {
      Value reference = value.getValue();
      FunctionCall call = resolver.resolveValue(reference, list); // function variable
      
      if(call == null) {
         handler.handleRuntimeError(INVOKE, scope, name, list);
      }
      return new Call2(call) {
         
         public Object invoke(Scope scope, Object source, Object... arguments) throws Exception{
            source = ((Value)source).getValue();
            return call.invoke(scope, source, arguments);
         }
      };  
   }
}