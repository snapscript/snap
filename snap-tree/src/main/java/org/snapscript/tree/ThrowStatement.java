package org.snapscript.tree;

import static org.snapscript.core.error.Reason.THROW;
import static org.snapscript.core.type.Phase.EXECUTE;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Execution;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.Reason;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.core.type.Phase;
import org.snapscript.core.variable.Value;

public class ThrowStatement implements Compilation {
   
   private final Statement control;
   
   public ThrowStatement(Evaluation evaluation) {
      this.control = new CompileResult(evaluation);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getConstruct(module, path, line);
      
      return new TraceStatement(interceptor, handler, control, trace);
   }
   
   private static class CompileResult extends Statement {
   
      private final Evaluation evaluation;
      
      public CompileResult(Evaluation evaluation) {
         this.evaluation = evaluation; 
      }   
      
      @Override
      public boolean define(Scope scope) throws Exception {
         evaluation.define(scope);
         return true;
      }
   
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         evaluation.compile(scope, null);
         return new CompileExecution(evaluation);
      }
   }
   
   private static class CompileExecution extends Execution {
      
      private final Evaluation evaluation;
      
      public CompileExecution(Evaluation evaluation) {
         this.evaluation = evaluation; 
      }   
   
      @Override
      public Result execute(Scope scope) throws Exception {
         Value reference = evaluation.evaluate(scope, null);
         Module module = scope.getModule();
         Context context = module.getContext();
         ErrorHandler handler = context.getHandler();
         Object value = reference.getValue();
         
         return handler.handleInternalError(THROW, scope, value); 
      }
   }

}