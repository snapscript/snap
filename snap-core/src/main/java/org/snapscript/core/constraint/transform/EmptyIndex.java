package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public class EmptyIndex implements GenericIndex {
   
   public EmptyIndex() {
      super();
   }

   @Override
   public Constraint getType(Constraint constraint, String name) {
      return null;
   }

}
