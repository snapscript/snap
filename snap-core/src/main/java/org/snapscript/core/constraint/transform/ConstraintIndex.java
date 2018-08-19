package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;

public interface ConstraintIndex {
   Constraint update(Scope scope, Constraint source, Constraint change);
}
