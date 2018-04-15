package org.snapscript.tree;

import static java.lang.Boolean.TRUE;
import static org.snapscript.core.result.Result.NORMAL;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Execution;
import org.snapscript.core.Identity;
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
import org.snapscript.parse.StringToken;

public class DebugStatement implements Compilation {
   
   private final Evaluation condition;

   public DebugStatement(){
      this(null, null);
   }

   public DebugStatement(StringToken token){
      this(null, token);
   }
   
   public DebugStatement(Evaluation condition){
      this(condition, null);
   }

   public DebugStatement(Evaluation condition, StringToken token){
      this.condition = condition;
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      Statement statement = create(module, path, line);
      
      return new TraceStatement(interceptor, handler, statement, trace);
   }
   
   private Statement create(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getDebug(module, path, line);
      
      return new CompileResult(interceptor, condition, trace);
   }
   
   private static class CompileResult extends Statement {
   
      private final TraceInterceptor interceptor;
      private final Evaluation evaluation;
      private final Evaluation success;
      private final Trace trace;

      public CompileResult(TraceInterceptor interceptor, Evaluation evaluation, Trace trace){
         this.success = new Identity(TRUE);
         this.interceptor = interceptor;
         this.evaluation = evaluation;
         this.trace = trace;
      }

      @Override
      public boolean define(Scope scope) throws Exception {
         if(evaluation != null) {
            evaluation.define(scope);
         }
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         if(evaluation != null) {
            evaluation.compile(scope, null);
            return new CompileExecution(interceptor, evaluation, trace);
         }
         return new CompileExecution(interceptor, success, trace);
      }
   }
   
   private static class CompileExecution extends Execution {
      
      private final TraceInterceptor interceptor;
      private final Evaluation evaluation;
      private final Trace trace;

      public CompileExecution(TraceInterceptor interceptor, Evaluation evaluation, Trace trace){
         this.interceptor = interceptor;
         this.evaluation = evaluation;
         this.trace = trace;
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Value value = evaluation.evaluate(scope, null);
         Object object = value.getValue();

         if(TRUE.equals(object)) {
            try {
               interceptor.traceBefore(scope, trace);
            } finally {
               interceptor.traceAfter(scope, trace);
            }
         }
         return NORMAL;
      }
   }
}