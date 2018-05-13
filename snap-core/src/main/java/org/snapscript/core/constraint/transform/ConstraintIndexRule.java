package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public class ConstraintIndexRule implements ConstraintRule {
   
   private final ConstraintIndex index;
   private final Constraint constraint; 

   public ConstraintIndexRule(Constraint constraint) {
      this(constraint, null);
   }
   
   public ConstraintIndexRule(Constraint constraint, ConstraintIndex index) {
      this.constraint = constraint;
      this.index = index;
   }
   
   @Override
   public Constraint getResult(Constraint original) {
      if(index != null) {
         return index.update(constraint, original);
      }
      return original;
   }
   
   @Override
   public Constraint getSource(){
      return constraint;
   }
}
