package org.snapscript.core.constraint.transform;

import org.snapscript.core.InternalStateException;
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
      
      if(constraint == null) {
         throw new InternalStateException("No constraint for '" + source + "'");
      }
      return new ConstraintHandle(constraint, index);
   }
}
