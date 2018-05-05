package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public class TypeTransform implements GenericTransform{
   
   private final GenericReference reference;
   
   public TypeTransform(Constraint constraint, Constraint result, GenericIndex index){
      this.reference = new GenericReference(constraint, result, index);
   }
   
   @Override
   public GenericReference getReference(Constraint source){
      return reference;
   }
}
