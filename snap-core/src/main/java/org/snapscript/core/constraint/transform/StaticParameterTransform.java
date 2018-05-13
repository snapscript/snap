package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public class StaticParameterTransform implements ConstraintTransform{
   
   private final ConstraintRule reference;
   
   public StaticParameterTransform(Constraint constraint){
      this.reference = new ConstraintIndexRule(constraint);
   }
   
   @Override
   public ConstraintRule apply(Constraint source){
      return reference;
   }
}
