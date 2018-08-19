package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.StaticConstraint;
import org.snapscript.core.type.Type;

public class TypeTransform implements ConstraintTransform {
   
   private final ConstraintRule reference;
   private final Constraint constriant;
   
   public TypeTransform(Type type) {
      this.constriant = new StaticConstraint(type);
      this.reference = new ConstraintIndexRule(constriant);
   }

   @Override
   public ConstraintRule apply(Constraint left) {
      return reference;
   }

}
