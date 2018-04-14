package org.snapscript.tree.condition;

import static org.snapscript.core.result.Result.NORMAL;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Execution;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.core.variable.Value;

public class IfStatement implements Compilation {
   
   private final Statement branch;
   
   public IfStatement(Evaluation evaluation, Statement positive) {
      this(evaluation, positive, null);
   }
   
   public IfStatement(Evaluation evaluation, Statement positive, Statement negative) {
      this.branch = new CompileResult(evaluation, positive, negative);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, branch, trace);
   }
   
   private static class CompileResult extends Statement {
   
      private final Evaluation condition;
      private final Statement positive;
      private final Statement negative;
      
      public CompileResult(Evaluation condition, Statement positive, Statement negative) {
         this.condition = condition;
         this.positive = positive;
         this.negative = negative;
      }
      
      @Override
      public boolean define(Scope scope) throws Exception {
         condition.define(scope);
         positive.define(scope);
         
         if(negative != null) {
            negative.define(scope);
         }       
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         Constraint result = condition.compile(scope, null);
         Execution success = positive.compile(scope, returns);         
         Execution failure = null;
         
         if(negative != null){
            failure = negative.compile(scope, returns);
         }         
         return new CompileExecution(condition, success, failure);
      }
   }
   
   private static class CompileExecution extends Execution {
      
      private final Evaluation condition;
      private final Execution positive;
      private final Execution negative;
      
      public CompileExecution(Evaluation condition, Execution positive, Execution negative) {
         this.condition = condition;
         this.positive = positive;
         this.negative = negative;
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Value result = condition.evaluate(scope, null);
         boolean value = result.getBoolean();
         
         if(value) {
            return positive.execute(scope);
         } else {
            if(negative != null) {
               return negative.execute(scope);
            }
         }            
         return NORMAL;
      }
   }
}