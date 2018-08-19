package org.snapscript.core.scope.index;

import org.snapscript.core.constraint.Constraint;

public interface Table extends Iterable<Local> {
   Local getLocal(int index);
   Constraint getConstraint(int index);
   void addLocal(int index, Local local);
   void addConstraint(int index, Constraint constraint);
}
