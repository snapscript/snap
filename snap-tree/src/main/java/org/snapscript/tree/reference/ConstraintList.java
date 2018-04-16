package org.snapscript.tree.reference;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;

public class ConstraintList extends Evaluation {

   private final Constraint[] constraints;

   public ConstraintList(Constraint... constraints) {
      this.constraints = constraints;
   }
}
