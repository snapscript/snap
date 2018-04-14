package org.snapscript.tree;

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

public class ExpressionStatement implements Compilation {
   
   private final Statement expression;
   
   public ExpressionStatement(Evaluation expression) {
      this.expression = new CompileResult(expression);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, expression, trace);
   }
   
   private static class CompileResult extends Statement {
      
      private final Evaluation expression;
   
      public CompileResult(Evaluation expression) {
         this.expression = expression;
      }
      
      @Override
      public boolean define(Scope scope) throws Exception {
         expression.define(scope);
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         expression.compile(scope, null);
         return new CompileExecution(expression);
      }
   }
   
   private static class CompileExecution extends Execution {
      
      private final Evaluation expression;
   
      public CompileExecution(Evaluation expression) {
         this.expression = expression;
      }      
   
      @Override
      public Result execute(Scope scope) throws Exception {
         Value reference = expression.evaluate(scope, null);
         Object value = reference.getValue();
         
         return Result.getNormal(value);
      }
   }
}