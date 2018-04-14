package org.snapscript.tree;

import static org.snapscript.core.result.Result.CONTINUE;
import static org.snapscript.core.result.Result.NORMAL;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Execution;
import org.snapscript.core.NoExecution;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.parse.StringToken;

public class ContinueStatement implements Compilation {
   
   private final Statement control;
   
   public ContinueStatement(StringToken token){
      this.control = new CompileResult();
   }   
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, control, trace);
   }
   
   private static class CompileResult extends Statement {
      
      private final Execution execution;
      
      public CompileResult() {
         this.execution = new NoExecution(CONTINUE);
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         return execution;
      }
   }
}