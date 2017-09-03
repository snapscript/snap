package org.snapscript.tree.operation;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public class CalculationResult extends Evaluation {

   private final Object value;

   public CalculationResult(Object value) {
      this.value = value;
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      return Value.getTransient(value);
   }

}