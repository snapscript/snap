package org.snapscript.core.trace;

import static org.snapscript.core.Order.OTHER;

import org.snapscript.core.Order;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Allocation;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.result.Result;

public class TraceAllocation extends Allocation {

   private final ErrorHandler handler;
   private final Allocation factory;
   private final Trace trace;
   
   public TraceAllocation(ErrorHandler handler, Allocation factory, Trace trace) {
      this.handler = handler;
      this.factory = factory;
      this.trace = trace;
   }
   
   @Override
   public Order define(Scope scope, Type type) throws Exception {
      try {
         return factory.define(scope, type);
      }catch(Exception cause) {
         handler.throwInternalError(scope, cause, trace);
      }
      return OTHER;
   }
   
   @Override
   public void compile(Scope scope, Type type) throws Exception {
      try {
         factory.compile(scope, type);
      }catch(Exception cause) {
         handler.throwInternalError(scope, cause, trace);
      }
   }
   
   @Override
   public void allocate(Scope scope, Type type) throws Exception {
      try {
         factory.allocate(scope, type);
      }catch(Exception cause) {
         handler.throwInternalError(scope, cause, trace);
      }
   }
   
   @Override
   public Result execute(Scope scope, Type type) throws Exception { 
      try {
         return factory.execute(scope, type);
      }catch(Exception cause) {
         handler.throwInternalError(scope, cause, trace);
      }
      return null;
   }

}
