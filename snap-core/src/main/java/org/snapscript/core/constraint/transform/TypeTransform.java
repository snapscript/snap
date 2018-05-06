package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public class TypeTransform implements GenericTransform{
   
   private final GenericHandle reference;
   
   public TypeTransform(Constraint constraint, GenericIndex index){
      this.reference = new GenericHandle(constraint, index);
   }
   
   @Override
   public GenericHandle getHandle(Constraint source){
      return reference;
   }
}
