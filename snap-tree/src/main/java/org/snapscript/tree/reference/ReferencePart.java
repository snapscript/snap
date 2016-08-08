package org.snapscript.tree.reference;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public class ReferencePart implements Evaluation {
   
   private final Evaluation evaluation;
   
   public ReferencePart(Evaluation evaluation) {
      this.evaluation = evaluation;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      return evaluation.evaluate(scope, left);
   }
}