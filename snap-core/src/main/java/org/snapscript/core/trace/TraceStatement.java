package org.snapscript.core.trace;

import static org.snapscript.core.result.Result.NORMAL;

import org.snapscript.core.Execution;
import org.snapscript.core.NoExecution;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.result.Result;

public class TraceStatement extends Statement {
   
   private final TraceInterceptor interceptor;
   private final ErrorHandler handler;
   private final Execution execution;
   private final Statement statement;
   private final Trace trace;
   
   public TraceStatement(TraceInterceptor interceptor, ErrorHandler handler, Statement statement, Trace trace) {
      this.execution = new NoExecution(NORMAL);
      this.interceptor = interceptor;
      this.statement = statement;
      this.handler = handler;
      this.trace = trace;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      try {
         statement.define(scope);
      }catch(Exception cause) {
         handler.throwInternalError(scope, cause, trace);
      }
   }
   
   @Override
   public Execution compile(Scope scope) throws Exception {
      try {
         Execution execution = statement.compile(scope);
         return new TraceExecution(interceptor, handler, execution, trace);
      }catch(Exception cause) {
         handler.throwInternalError(scope, cause, trace);
      }
      return execution;
   }
   
   private static class TraceExecution extends Execution {
      
      private final TraceInterceptor interceptor;
      private final ErrorHandler handler;
      private final Execution statement;
      private final Trace trace;
      
      public TraceExecution(TraceInterceptor interceptor, ErrorHandler handler, Execution statement, Trace trace) {
         this.interceptor = interceptor;
         this.statement = statement;
         this.handler = handler;
         this.trace = trace;
      }
   
      @Override
      public Result execute(Scope scope) throws Exception {
         try {
            interceptor.before(scope, trace);
            return statement.execute(scope); 
         } catch(Exception cause) {
            interceptor.error(scope, trace, cause);
            return handler.throwInternalError(scope, cause);
         } finally {
            interceptor.after(scope, trace);
         }
      }
   }
}