package org.snapscript.tree.constraint;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;

public class SafeConstraint implements Constraint {
   
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
}