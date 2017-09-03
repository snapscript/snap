package org.snapscript.core.trace;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;

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
   public Value compile(Scope scope, Object left) throws Exception {
      try {
         return evaluation.compile(scope, left);
      }catch(Exception e) {
         System.err.println("trace="+trace);
         e.printStackTrace();
         throw e;
      }
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