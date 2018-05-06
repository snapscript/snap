package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public class IdentityTransform implements GenericTransform{
   
   private final GenericIndex index;
   
   public IdentityTransform(GenericIndex index){
      this.index = index;
   }
   
   @Override
   public GenericHandle getHandle(Constraint source){
      return new GenericHandle(source, index);
   }
}
