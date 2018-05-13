package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public interface ConstraintRule {
   Constraint getResult(Constraint original);
   Constraint getSource();
}
