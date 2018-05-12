package org.snapscript.core.variable.bind;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.variable.Value;

public interface VariableResult<T> {
   Constraint getConstraint(Constraint left);
   Value getValue(T left);
}
