package org.snapscript.tree.constraint;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;

public class ClassConstraint extends TypeConstraint {
   
   public ClassConstraint(Evaluation evaluation) {
      super(evaluation);
   }
   
   public ClassConstraint(Evaluation evaluation, Constraint context) {
      super(evaluation, context);
   }
}
