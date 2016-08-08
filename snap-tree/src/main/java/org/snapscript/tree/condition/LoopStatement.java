package org.snapscript.tree.condition;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.core.trace.TraceType;

public class LoopStatement implements Compilation {
   
   private final Statement loop;
   
   public LoopStatement(Statement statement) {
      this.loop = new CompileResult(statement);
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
      
      private final Statement body;
      
      public CompileResult(Statement body) {
         this.body = body;
      }
      
      @Override
      public Result compile(Scope scope) throws Exception {   
         return body.compile(scope);
      }
   
      @Override
      public Result execute(Scope scope) throws Exception {
         Scope compound = scope.getInner();
         
         while(true) {
            Result result = body.execute(compound);
            
            if(result.isReturn()) {
               return result;
            }
            if(result.isBreak()) {
               return ResultType.getNormal();
            }
         }
      }
   }
}
