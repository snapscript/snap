package org.snapscript.core.constraint.transform;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.type.Type;

public class GenericTransform implements ConstraintTransform{
   
   private final GenericConstraintBuilder builder;
   private final ConstraintIndex index;
   
   public GenericTransform(Type type, ConstraintIndex index, ConstraintTransform[] list) {
      this.builder = new GenericConstraintBuilder(type, list);
      this.index = index;
   }
   
   @Override
   public ConstraintHandle apply(Constraint source){
      Constraint constraint = builder.create(source);
      
      if(constraint == null) {
         throw new InternalStateException("No constraint for '" + source + "'");
      }
      return new ConstraintHandle(constraint, index);
   }
}
