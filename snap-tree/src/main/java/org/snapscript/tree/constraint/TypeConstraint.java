package org.snapscript.tree.constraint;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public class TypeConstraint implements Evaluation {
   
   private final Evaluation constraint;
   
   public TypeConstraint(Evaluation constraint) {
      this.constraint = constraint;
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      return constraint.evaluate(scope, null);
   }

}
