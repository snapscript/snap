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

public class ThrowStatement implements Compilation {
   
   private final Statement control;
   
   public ThrowStatement(Evaluation evaluation) {
      this.control = new CompileResult(evaluation);
   }
   
   @Override
   public Statement compile(Module module, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = TraceType.getConstruct(module, line);
      
      return new TraceStatement(interceptor, handler, control, trace);
   }
   
   private static class CompileResult extends Statement {
   
      private final Evaluation evaluation;
      
      public CompileResult(Evaluation evaluation) {
         this.evaluation = evaluation; 
      }   
   
      @Override
      public Result execute(Scope scope) throws Exception {
         Value reference = evaluation.evaluate(scope, null);
         Module module = scope.getModule();
         Context context = module.getContext();
         ErrorHandler handler = context.getHandler();
         Object value = reference.getValue();
         
         return handler.throwInternal(scope, value); 
      }
   }

}
