package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public class ConstraintHandle {
   
   private final ConstraintIndex index;
   private final Constraint constraint; 

   public ConstraintHandle(Constraint constraint) {
      this(constraint, null);
   }
   
   public ConstraintHandle(Constraint constraint, ConstraintIndex index) {
      this.constraint = constraint;
      this.index = index;
   }
   
   public Constraint getConstraint(Constraint original) {
      if(index != null) {
         return index.update(constraint, original);
      }
      return original;
   }
   
   public Constraint getType(){
      return constraint;
   }
}
