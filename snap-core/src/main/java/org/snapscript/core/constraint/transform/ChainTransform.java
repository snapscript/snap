package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public class ChainTransform implements ConstraintTransform {
   
   private final ConstraintTransform[] path;
   
   public ChainTransform(ConstraintTransform[] path){
      this.path = path;
   }
   
   public ConstraintHandle apply(Constraint constraint){
      ConstraintHandle handle = null;
      
      for(ConstraintTransform transform : path) {
         handle = transform.apply(constraint);         
         constraint = handle.getType();
      }
      return handle;
   }
}
