package org.snapscript.core.variable.bind;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.index.Address;
import org.snapscript.core.variable.Value;

public interface VariableResult<T> {
   Address getAddress(int offset);
   Constraint getConstraint(Constraint left);
   Value getValue(T left);
}
