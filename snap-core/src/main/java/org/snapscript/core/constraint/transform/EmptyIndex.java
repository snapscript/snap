package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;

public class EmptyIndex implements ConstraintIndex {
   
   public EmptyIndex() {
      super();
   }

   @Override
   public Constraint update(Scope scope, Constraint source, Constraint change) {
      return change;
   }
}
