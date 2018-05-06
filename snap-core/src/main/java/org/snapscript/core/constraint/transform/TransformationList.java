package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public class TransformationList implements GenericTransform {
   
   private final GenericTransform[] path;
   
   public TransformationList(GenericTransform[] path){
      this.path = path;
   }
   
   public GenericHandle getHandle(Constraint constraint){
      GenericHandle handle = null;
      
      for(GenericTransform transform : path) {
         handle = transform.getHandle(constraint);         
         constraint = handle.getType();
      }
      return handle;
   }
}
