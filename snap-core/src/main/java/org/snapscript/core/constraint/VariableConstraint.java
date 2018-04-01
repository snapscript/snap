package org.snapscript.core.constraint;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

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
