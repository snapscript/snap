package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public class EmptyIndex implements ConstraintIndex {
   
   public EmptyIndex() {
      super();
   }

   @Override
   public Constraint resolve(Constraint constraint, String name) {
      return null;
   }

}
