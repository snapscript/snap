package org.snapscript.core.constraint.transform;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.constraint.Constraint;

public class GenericReference {
   
   private final Constraint constraint;
   private final Constraint result;   
   private final GenericIndex index;

   public GenericReference(Constraint constraint, Constraint result) {
      this(constraint, result, null);
   }
   
   public GenericReference(Constraint constraint, Constraint result, GenericIndex index) {
      this.constraint = constraint;
      this.result = result;
      this.index = index;
   }
   
   public Constraint getConstraint(String name) {
      if(index != null) {
         return index.getType(constraint, name);         
      }
      return NONE;
   }
   
   public Constraint getType(){
      return result;
   }
}
