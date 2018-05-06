package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public class StaticParameterTransform implements ConstraintTransform{
   
   private final ConstraintHandle reference;
   
   public StaticParameterTransform(Constraint constraint){
      this.reference = new ConstraintHandle(constraint);
   }
   
   @Override
   public ConstraintHandle apply(Constraint source){
      return reference;
   }
}
