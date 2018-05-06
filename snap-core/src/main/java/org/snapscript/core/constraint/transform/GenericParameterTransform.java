package org.snapscript.core.constraint.transform;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;

public class GenericParameterTransform implements ConstraintTransform {
   
   private final ConstraintIndex index;
   private final String name;
   
   public GenericParameterTransform(ConstraintIndex index, String name){
      this.index = index;
      this.name = name;
   }
   
   @Override
   public ConstraintHandle apply(Constraint source){
      Constraint constraint = index.resolve(source, name);
      
      if(constraint == null){
         throw new InternalStateException("Generic parameter '" + name + "' not found for " + source);
      }
      return new ConstraintHandle(constraint, index);
   }
}
