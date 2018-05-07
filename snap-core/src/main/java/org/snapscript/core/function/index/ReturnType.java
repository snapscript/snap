package org.snapscript.core.function.index;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;

public interface ReturnType {
   Constraint getConstraint(Scope scope, Constraint left);
}
