package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public class StaticTransform implements ConstraintTransform{
   
   private final ConstraintHandle reference;
   
   public StaticTransform(Constraint constraint, ConstraintIndex index){
      this.reference = new ConstraintHandle(constraint, index);
   }
   
   @Override
   public ConstraintHandle apply(Constraint source){
      return reference;
   }
}
