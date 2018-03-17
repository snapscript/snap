package org.snapscript.tree;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Value;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.parse.StringToken;

public class YieldStatement implements Compilation {
   
   private final Statement control;
   
   public YieldStatement(StringToken token){
      this(null, token);
   }
   
   public YieldStatement(Evaluation evaluation){
      this(evaluation, null);
   }
   
   public YieldStatement(Evaluation evaluation, StringToken token){
      this.control = new CompileResult(evaluation);
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
   
      private final Evaluation evaluation;
      private final Result result;

      public CompileResult(Evaluation evaluation){
         this.result = Result.getYield();
         this.evaluation = evaluation;
      }
      
      @Override
      public void define(Scope scope) throws Exception {
         if(evaluation != null) {
            evaluation.define(scope);
         }
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         if(evaluation != null) {
            Value value = evaluation.evaluate(scope, null);
            Object object = value.getValue();

            return Result.getYield(object);
         }
         return result;
      }
   }
}