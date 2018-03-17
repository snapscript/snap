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
   public void define(Scope scope) throws Exception {
      try {
         statement.define(scope);
      }catch(Exception cause) {
         handler.throwInternalError(scope, cause, trace);
      }
   }
   
   @Override
   public void compile(Scope scope) throws Exception {
      try {
         statement.compile(scope);
      }catch(Exception cause) {
         cause.printStackTrace();
         handler.throwInternalError(scope, cause, trace);
      }
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