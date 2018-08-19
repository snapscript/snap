package org.snapscript.tree.constraint;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;

public class TraitConstraint extends ClassConstraint {
   
   public TraitConstraint(Evaluation evaluation) {
      super(evaluation);
   }
   
   public TraitConstraint(Evaluation evaluation, Constraint context) {
      super(evaluation, context);
   }
}
