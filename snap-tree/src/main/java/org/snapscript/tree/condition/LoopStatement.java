package org.snapscript.tree.condition;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.error.ErrorHandler;
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
   
   private static class CompileResult extends SuspendStatement<Object> {
      
      private final Statement body;
      
      public CompileResult(Statement body) {
         this.body = body;
      }
      
      @Override
      public void define(Scope scope) throws Exception {   
         body.define(scope);
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
               return Result.getNormal();
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