package org.snapscript.core.trace;

import static org.snapscript.core.error.Reason.THROW;
import static org.snapscript.core.type.Order.OTHER;

import org.snapscript.core.error.Reason;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Allocation;
import org.snapscript.core.type.Order;
import org.snapscript.core.type.Type;

public class TraceAllocation extends Allocation {

   private final TraceInterceptor interceptor;
   private final ErrorHandler handler;
   private final Allocation factory;
   private final Trace trace;
   
   public TraceAllocation(TraceInterceptor interceptor, ErrorHandler handler, Allocation factory, Trace trace) {
      this.interceptor = interceptor;
      this.handler = handler;
      this.factory = factory;
      this.trace = trace;
   }
   
   @Override
   public Order define(Scope scope, Type type) throws Exception {
      try {
         return factory.define(scope, type);
      }catch(Exception cause) {
         interceptor.traceCompileError(scope, trace, cause);
      }
      return OTHER;
   }
   
   @Override
   public void compile(Scope scope, Type type) throws Exception {
      try {
         factory.compile(scope, type);
      }catch(Exception cause) {
         interceptor.traceCompileError(scope, trace, cause);
      }
   }
   
   @Override
   public void allocate(Scope scope, Type type) throws Exception {
      try {
         factory.allocate(scope, type);
      }catch(Exception cause) {
         handler.handleInternalError(THROW, scope, cause, trace);
      }
   }
   
   @Override
   public Result execute(Scope scope, Type type) throws Exception { 
      try {
         return factory.execute(scope, type);
      }catch(Exception cause) {
         handler.handleInternalError(THROW, scope, cause, trace);
      }
      return null;
   }

}
