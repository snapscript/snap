package org.snapscript.core.variable.index;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public interface VariablePointer<T> {
   Constraint getConstraint(Scope scope, Constraint left);
   Value getValue(Scope scope, T left);
}