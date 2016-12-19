package org.snapscript.core.stack;

import org.snapscript.core.Result;
import org.snapscript.core.Statement;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;

public class TraceStatement2 { // TraceEvaluation does not need any change it does not deal with the stack!!
   
   private final TraceInterceptor interceptor;
   private final ErrorHandler handler;
   private final Statement statement;
   private final Trace trace;
   
   public TraceStatement2(TraceInterceptor interceptor, ErrorHandler handler, Statement statement, Trace trace) {
      this.interceptor = interceptor;
      this.statement = statement;
      this.handler = handler;
      this.trace = trace;
   }
   
   public Result compile(Scope2 scope) throws Exception {
      return null; //statement.compile(scope);
   }
   
   public Result execute(Scope2 scope) throws Exception {
      //
      // MAYBE NOT HERE AS THE INVOCATION NEEDS TO GRAB THE PARAMETERS
      // FROM THE PREVIOUS STACK/STATE, once you pass the parameters
      // from the previous stack on to the new stack then you can
      // grow the stack
      //
      State2 stack = scope.getStack();
      State2 outer = stack.create(); // allow the stack to grow!! 
      
      try {
         //interceptor.before(scope, trace);
         //return statement.execute(scope); 
         return null;
      } catch(Exception cause) {
         //return handler.throwInternal(scope, cause);
         return null;
      } finally {
         //interceptor.after(scope, trace);
         outer.clear(); // this clears it up to the initial stack point
      }
   }
}