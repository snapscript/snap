package org.snapscript.core.function.index;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;

public class StaticReturnType implements ReturnType {
   
   private final Constraint returns;

   public StaticReturnType(Constraint returns) {
      this.returns = returns;
   }

   @Override
   public Constraint getConstraint(Scope scope, Constraint left) {
      return returns;
   }
}