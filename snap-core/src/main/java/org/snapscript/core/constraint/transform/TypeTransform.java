package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public class TypeTransform implements ConstraintTransform{
   
   private final ConstraintHandle reference;
   
   public TypeTransform(Constraint constraint, ConstraintIndex index){
      this.reference = new ConstraintHandle(constraint, index);
   }
   
   @Override
   public ConstraintHandle apply(Constraint source){
      return reference;
   }
}
