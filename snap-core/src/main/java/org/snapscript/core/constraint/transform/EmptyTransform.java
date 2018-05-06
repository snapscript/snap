package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.StaticConstraint;
import org.snapscript.core.type.Type;

public class EmptyTransform implements ConstraintTransform {
   
   private final ConstraintHandle reference;
   private final Constraint constriant;
   
   public EmptyTransform(Type type) {
      this.constriant = new StaticConstraint(type);
      this.reference = new ConstraintHandle(constriant);
   }

   @Override
   public ConstraintHandle apply(Constraint constraint) {
      return reference;
   }

}
