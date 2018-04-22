package org.snapscript.core.function.dispatch;

import static org.snapscript.core.constraint.Constraint.NONE;
import static org.snapscript.core.error.Reason.INVOKE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class ValueDispatcher implements FunctionDispatcher<Value> {
   
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
   public Value dispatch(Scope scope, Value value, Object... list) throws Exception {
      FunctionCall closure = resolver.resolveValue(value, list); // function variable
      
      if(closure == null) {
         handler.handleRuntimeError(INVOKE, scope, name, list);
      }
      return closure.call();   
   }
}