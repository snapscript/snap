package org.snapscript.core.constraint;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;

public class VariableConstraint extends Constraint {
   
   private final Type type;
   
   public VariableConstraint(Type type) {
      this.type = type;
   }

   @Override
   public Type getType(Scope scope) {
      return type;
   }

   @Override
   public boolean isVariable() {
      return true;
   }
}
