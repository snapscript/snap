package org.snapscript.core.constraint.transform;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;

public class IndexTransform implements GenericTransform {
   
   private final GenericIndex index;
   private final String name;
   
   public IndexTransform(GenericIndex index, String name){
      this.index = index;
      this.name = name;
   }
   
   @Override
   public GenericHandle getHandle(Constraint source){
      Constraint constraint = index.getType(source, name);
      
      if(constraint == null){
         throw new InternalStateException("Generic parameter '" + name + "' not found for " + source);
      }
      return new GenericHandle(constraint, index);
   }
}
