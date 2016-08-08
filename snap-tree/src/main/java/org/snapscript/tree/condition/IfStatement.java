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

public class IfStatement implements Compilation {
   
   private final Statement branch;
   
   public IfStatement(Evaluation evaluation, Statement positive) {
      this(evaluation, positive, null);
   }
   
   public IfStatement(Evaluation evaluation, Statement positive, Statement negative) {
      this.branch = new CompileResult(evaluation, positive, negative);
   }
   
   @Override
   public Statement compile(Module module, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = TraceType.getNormal(module, line);
      
      return new TraceStatement(interceptor, handler, branch, trace);
   }
   
   private static class CompileResult extends Statement {
   
      private final Evaluation condition;
      private final Statement positive;
      private final Statement negative;
      
      public CompileResult(Evaluation evaluation, Statement positive, Statement negative) {
         this.condition = evaluation;
         this.positive = positive;
         this.negative = negative;
      }
      
      @Override
      public Result compile(Scope scope) throws Exception {
         if(negative != null) {
            negative.compile(scope);
         }       
         return positive.compile(scope);
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Value result = condition.evaluate(scope, null);
         Boolean value = result.getBoolean();
         
         if(value.booleanValue()) {
            return positive.execute(scope);
         } else {
            if(negative != null) {
               return negative.execute(scope);
            }
         }            
         return ResultType.getNormal();
      }
   }
}