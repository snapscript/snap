package org.snapscript.core.function.dispatch;

import static org.snapscript.core.constraint.Constraint.NONE;
import static org.snapscript.core.error.Reason.INVOKE;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.index.FunctionAdapter;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class ClosureDispatcher implements FunctionDispatcher {

   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final String name;      
   
   public ClosureDispatcher(FunctionResolver resolver, ErrorHandler handler, String name) {
      this.handler = handler;
      this.resolver = resolver;
      this.name = name;
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception { 
      return NONE;
   }

   @Override
   public Call2 dispatch(Scope scope, Value value, Object... arguments) throws Exception {
      Function function = value.getValue();
      Call2 call = bind(scope, function, arguments); // this is not used often
      
      if(call == null) {
         handler.handleRuntimeError(INVOKE, scope, function, name, arguments);
      }
      return call;
   }
   
   private Call2 bind(Scope scope, Function function, Object... arguments) throws Exception {
      FunctionCall call = resolver.resolveInstance(scope, function, name, arguments); // this is not used often
      
      if(call == null) {
         Object adapter = new FunctionAdapter(function);
         
         call = resolver.resolveInstance(scope, adapter, name, arguments);
         
         if(call != null) {
            return new Call2(call) {
               
               public Object invoke(Scope scope, Object source, Object... arguments) throws Exception{
                  Function function = ((Value)source).getValue();
                  source = new FunctionAdapter(function);
                  return call.invoke(scope, source, arguments);
               }
            };
         }
         return null;
      }
      return new Call2(call) {
         
         public Object invoke(Scope scope, Object source, Object... arguments) throws Exception{
            source = ((Value)source).getValue();
            return call.invoke(scope, source, arguments);
         }
      };
   }
}