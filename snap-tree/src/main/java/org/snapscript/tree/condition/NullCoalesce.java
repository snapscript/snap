package org.snapscript.tree.condition;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public class NullCoalesce implements Evaluation {

   private final Evaluation substitute;
   private final Evaluation evaluation;
   
   public NullCoalesce(Evaluation evaluation, Evaluation substitute) {
      this.evaluation = evaluation;
      this.substitute = substitute;
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Value result = evaluation.evaluate(scope, null);
      Object value = result.getValue();
      System.out.println(value);
      if(value == null) {
         return substitute.evaluate(scope, left);
      } 
      return result;
   }
}
