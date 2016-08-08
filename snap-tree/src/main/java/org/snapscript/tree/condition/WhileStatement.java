package org.snapscript.tree.condition;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Value;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.core.trace.TraceType;

public class WhileStatement implements Compilation {
   
   private final Statement loop;
   
   public WhileStatement(Evaluation evaluation, Statement body) {
      this.loop = new CompileResult(evaluation, body);
   }
   
   @Override
   public Statement compile(Module module, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = TraceType.getNormal(module, line);
      
      return new TraceStatement(interceptor, handler, loop, trace);
   }
   
   private static class CompileResult extends Statement {
   
      private final Evaluation condition;
      private final Statement body;
      
      public CompileResult(Evaluation evaluation, Statement body) {
         this.condition = evaluation;
         this.body = body;
      }
      
      @Override
      public Result compile(Scope scope) throws Exception {   
         return body.compile(scope);
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         while(true) {
            Value result = condition.evaluate(scope, null);
            Boolean value = result.getBoolean();
            
            if(value.booleanValue()) {
               Result next = body.execute(scope);
               
               if(next.isReturn()) {
                  return next;
               }
               if(next.isBreak()) {
                  return ResultType.getNormal();
               }
            } else {
               return ResultType.getNormal();
            } 
         }
      }
   }
}