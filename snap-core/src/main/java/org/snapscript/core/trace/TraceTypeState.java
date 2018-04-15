package org.snapscript.core.trace;

import static org.snapscript.core.error.Reason.THROW;
import static org.snapscript.core.type.Order.OTHER;

import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Order;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeState;

public class TraceTypeState extends TypeState {

   private final TraceInterceptor interceptor;
   private final ErrorHandler handler;
   private final TypeState state;
   private final Trace trace;
   
   public TraceTypeState(TraceInterceptor interceptor, ErrorHandler handler, TypeState state, Trace trace) {
      this.interceptor = interceptor;
      this.handler = handler;
      this.state = state;
      this.trace = trace;
   }
   
   @Override
   public Order define(Scope scope, Type type) throws Exception {
      try {
         return state.define(scope, type);
      }catch(Exception cause) {
         interceptor.traceCompileError(scope, trace, cause);
      }
      return OTHER;
   }
   
   @Override
   public void compile(Scope scope, Type type) throws Exception {
      try {
         state.compile(scope, type);
      }catch(Exception cause) {
         interceptor.traceCompileError(scope, trace, cause);
      }
   }
   
   @Override
   public void allocate(Scope scope, Type type) throws Exception {
      try {
         state.allocate(scope, type);
      }catch(Exception cause) {
         handler.handleInternalError(THROW, scope, cause, trace);
      }
   }
   
   @Override
   public Result execute(Scope scope, Type type) throws Exception { 
      try {
         return state.execute(scope, type);
      }catch(Exception cause) {
         handler.handleInternalError(THROW, scope, cause, trace);
      }
      return null;
   }

}
