package org.snapscript.core.trace;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public class TraceEvaluation extends Evaluation {

   private final TraceInterceptor interceptor;
   private final Evaluation evaluation;
   private final Trace trace;
   
   public TraceEvaluation(TraceInterceptor interceptor, Evaluation evaluation, Trace trace) {
      this.interceptor = interceptor;
      this.evaluation = evaluation;
      this.trace = trace;
   }

   @Override
   public void define(Scope scope) throws Exception {
      evaluation.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      try {
         return evaluation.compile(scope, left);
      }catch(Exception cause) {
         interceptor.traceCompileError(scope, trace, cause);
      }
      return NONE;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      try {
         interceptor.traceBefore(scope, trace);
         return evaluation.evaluate(scope, left); 
      } finally {
         interceptor.traceAfter(scope, trace);
      }
   }
}