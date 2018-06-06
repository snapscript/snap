package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;

public class GenericParameterTransform implements ConstraintTransform {
   
   private final ConstraintIndex index;
   private final Constraint next;
   
   public GenericParameterTransform(ConstraintIndex index, Constraint next){
      this.index = index;
      this.next = next;
   }
   
   @Override
   public ConstraintRule apply(Constraint source){
      Constraint constraint = index.update(source, next);
      
      if(constraint == null) {
         throw new InternalStateException("No constraint for '" + source + "'");
      }
      return new ConstraintIndexRule(constraint, index);
   }
}
