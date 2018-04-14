package org.snapscript.tree.condition;

import static org.snapscript.core.result.Result.NORMAL;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
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
import org.snapscript.core.yield.Resume;
import org.snapscript.core.yield.Yield;
import org.snapscript.tree.SuspendStatement;

public class LoopStatement implements Compilation {
   
   private final Statement loop;
   
   public LoopStatement(Statement statement) {
      this.loop = new CompileResult(statement);
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
      
      private final Statement body;
      
      public CompileResult(Statement body) {
         this.body = body;
      }
      
      @Override
      public boolean define(Scope scope) throws Exception {   
         body.define(scope);
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         Execution execution = body.compile(scope, returns);
         return new CompileExecution(execution);
      }
   }
   
   
   private static class CompileExecution extends SuspendStatement<Object> {
      
      private final Execution body;
      
      public CompileExecution(Execution body) {
         this.body = body;
      }
   
      @Override
      public Result execute(Scope scope) throws Exception {
         return resume(scope, null);
      }
      
      @Override
      public Result resume(Scope scope, Object data) throws Exception {
         while(true) {
            Result result = body.execute(scope);
            
            if(result.isYield()) {
               return suspend(scope, result, this, null);
            }
            if(result.isReturn()) {
               return result;
            }
            if(result.isBreak()) {
               return NORMAL;
            }
         }
      }

      @Override
      public Resume suspend(Result result, Resume resume, Object value) throws Exception {
         Yield yield = result.getValue();
         Resume child = yield.getResume();
         
         return new LoopResume(child, resume);
      }
   }
}