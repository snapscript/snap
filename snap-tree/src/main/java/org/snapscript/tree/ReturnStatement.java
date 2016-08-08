package org.snapscript.tree;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Value;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.core.trace.TraceType;
import org.snapscript.parse.StringToken;

public class ReturnStatement implements Compilation {
   
   private final Statement control;
   
   public ReturnStatement(StringToken token){
      this(null, token);
   }
   
   public ReturnStatement(Evaluation evaluation){
      this(evaluation, null);
   }
   
   public ReturnStatement(Evaluation evaluation, StringToken token){
      this.control = new CompileResult(evaluation);
   }
   
   @Override
   public Statement compile(Module module, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = TraceType.getNormal(module, line);
      
      return new TraceStatement(interceptor, handler, control, trace);
   }
   
   private static class CompileResult extends Statement {
   
      private final Evaluation evaluation;

      public CompileResult(Evaluation evaluation){
         this.evaluation = evaluation;
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         if(evaluation != null) {
            Value value = evaluation.evaluate(scope, null);
            Object object = value.getValue();
            
            return ResultType.getReturn(object);
         }
         return ResultType.getReturn();
      }
   }
}