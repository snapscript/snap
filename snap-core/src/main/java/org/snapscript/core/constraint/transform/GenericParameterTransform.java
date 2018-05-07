package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public class GenericParameterTransform implements ConstraintTransform {
   
   private final ConstraintIndex index;
   private final Constraint next;
   
   public GenericParameterTransform(ConstraintIndex index, Constraint next){
      this.index = index;
      this.next = next;
   }
   
   @Override
   public ConstraintHandle apply(Constraint source){
      Constraint constraint = index.update(source, next);
      return new ConstraintHandle(constraint, index);
   }
}
