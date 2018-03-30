package org.snapscript.tree.constraint;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.constraint.Constraint;

public class SafeConstraint extends Constraint {
   
   private final Constraint constraint;
   
   public SafeConstraint(Constraint constraint) {
      this.constraint = constraint;
   }

   @Override
   public Type getType(Scope scope) {
      if(constraint != null) {
         return constraint.getType(scope);
      }
      return null;
   }
   
   @Override
   public boolean isInstance() {
      if(constraint != null) {
         return constraint.isInstance();
      }
      return true;
   }
   
   @Override
   public boolean isStatic() {
      if(constraint != null) {
         return constraint.isStatic();
      }
      return false;
   }
   
   @Override
   public boolean isModule() {
      if(constraint != null) {
         return constraint.isModule();
      }
      return false;
   }
}