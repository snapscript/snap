package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public class TransformationList implements GenericTransform {
   
   private final GenericTransform[] path;
   
   public TransformationList(GenericTransform[] path){
      this.path = path;
   }
   
   public GenericReference getReference(Constraint constraint){
      GenericReference reference = null;
      
      for(GenericTransform transform : path) {
         reference = transform.getReference(constraint);         
         constraint = reference.getType();
      }
      return reference;
   }
}
