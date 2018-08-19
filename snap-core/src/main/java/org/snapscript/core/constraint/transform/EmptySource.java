package org.snapscript.core.constraint.transform;

import static java.util.Collections.EMPTY_LIST;

import java.util.List;

import org.snapscript.core.constraint.Constraint;

public class EmptySource implements ConstraintSource {
   
   public EmptySource() {
      super();
   }

   @Override
   public List<Constraint> getConstraints(Constraint constraint) {
      return EMPTY_LIST;
   }

}
