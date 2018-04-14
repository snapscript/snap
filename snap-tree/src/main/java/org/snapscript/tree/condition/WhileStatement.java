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
import org.snapscript.core.yield.Resume;
import org.snapscript.core.yield.Yield;
import org.snapscript.tree.SuspendStatement;

public class WhileStatement implements Compilation {
   
   private final Statement loop;
   
   public WhileStatement(Evaluation evaluation, Statement body) {
      this.loop = new CompileResult(evaluation, body);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, loop, trace);
   }
   
   private static class CompileResult extends Statement {
   
      private final Evaluation condition;
      private final Statement body;
      
      public CompileResult(Evaluation condition, Statement body) {
         this.condition = condition;
         this.body = body;
      }
      
      @Override
      public boolean define(Scope scope) throws Exception { 
         condition.define(scope);
         body.define(scope);
         return true;
      }
   
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         Constraint constraint = condition.compile(scope, null);
         Execution execution = body.compile(scope, returns);
         
         return new CompileExecution(condition, execution);   
      }
   }
   
   private static class CompileExecution extends SuspendStatement<Object> {
      
      private final Evaluation condition;
      private final Execution body;
      
      public CompileExecution(Evaluation condition, Execution body) {
         this.condition = condition;
         this.body = body;
      }

      @Override
      public Result execute(Scope scope) throws Exception {
         return resume(scope, null);
      }
      
      @Override
      public Result resume(Scope scope, Object data) throws Exception {
         while(true) {
            Value result = condition.evaluate(scope, null);
            boolean value = result.getBoolean();
            
            if(value) {
               Result next = body.execute(scope);
               
               if(next.isYield()) {
                  return suspend(scope, next, this, null);
               }
               if(next.isReturn()) {
                  return next;
               }
               if(next.isBreak()) {
                  return NORMAL;
               }
            } else {
               return NORMAL;
            } 
         }
      }

      @Override
      public Resume suspend(Result result, Resume resume, Object value) throws Exception {
         Yield yield = result.getValue();
         Resume child = yield.getResume();
         
         return new WhileResume(child, resume);
      }
   }
}