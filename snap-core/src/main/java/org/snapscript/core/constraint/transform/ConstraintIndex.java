package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public interface ConstraintIndex {
   Constraint resolve(Constraint constraint, String name);
}
