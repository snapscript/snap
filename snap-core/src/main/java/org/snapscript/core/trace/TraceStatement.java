package org.snapscript.core.trace;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.error.ErrorHandler;

public class TraceStatement extends Statement {
   
   private final TraceInterceptor interceptor;
   private final ErrorHandler handler;
   private final Statement statement;
   private final Trace trace;
   
   public TraceStatement(TraceInterceptor interceptor, ErrorHandler handler, Statement statement, Trace trace) {
      this.interceptor = interceptor;
      this.statement = statement;
      this.handler = handler;
      this.trace = trace;
   }
   
   @Override
   public Result compile(Scope scope) throws Exception {
      return statement.compile(scope);
   }
   
   @Override
   public Result execute(Scope scope) throws Exception {
      try {
         interceptor.before(scope, trace);
         return statement.execute(scope); 
      } catch(Exception cause) {
         return handler.throwInternal(scope, cause);
      } finally {
         interceptor.after(scope, trace);
      }
   }
}
