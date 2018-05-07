package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public interface ConstraintIndex {
   Constraint update(Constraint source, Constraint change);
}
