package org.snapscript.core.trace;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;

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
      return evaluation.compile(scope, left); 
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      try {
         interceptor.before(scope, trace);
         return evaluation.evaluate(scope, left); 
      } finally {
         interceptor.after(scope, trace);
      }
   }
}