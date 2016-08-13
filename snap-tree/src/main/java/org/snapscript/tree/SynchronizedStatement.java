package org.snapscript.tree;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Value;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.core.trace.TraceType;

public class SynchronizedStatement implements Compilation {
   
   private final Statement statement;
   
   public SynchronizedStatement(Evaluation evaluation, Statement statement) {
      this.statement = new CompileResult(evaluation, statement);
   }
   
   @Override
   public Statement compile(Module module, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = TraceType.getNormal(module, line);
      
      return new TraceStatement(interceptor, handler, statement, trace);
   }
   
   private static class CompileResult extends Statement {

      private final Evaluation reference;
      private final Statement statement;
      
      public CompileResult(Evaluation reference, Statement statement) {
         this.reference = reference;
         this.statement = statement;
      }
      
      @Override
      public Result compile(Scope scope) throws Exception {
         return statement.compile(scope);
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Value value = reference.evaluate(scope, null);
         Object object = value.getValue();
         
         synchronized(object) {
            return statement.execute(scope);
         }
      }
   }
}
