package org.snapscript.core.constraint.transform;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.constraint.Constraint;

public class ConstraintHandle {
   
   private final Constraint constraint;   
   private final ConstraintIndex index;

   public ConstraintHandle(Constraint constraint) {
      this(constraint, null);
   }
   
   public ConstraintHandle(Constraint constraint, ConstraintIndex index) {
      this.constraint = constraint;
      this.index = index;
   }
   
   public Constraint getConstraint(String name) {
      if(index != null) {
         return index.resolve(constraint, name);         
      }
      return NONE;
   }
   
   public Constraint getType(){
      return constraint;
   }
}
