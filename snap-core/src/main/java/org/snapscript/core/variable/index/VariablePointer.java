package org.snapscript.core.variable.index;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public interface VariablePointer<T> {
   Constraint compile(Scope scope, Constraint left);
   Value get(Scope scope, T left);
}