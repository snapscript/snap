package org.snapscript.tree.variable.index;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;

public interface VariablePointer<T> {
   Constraint compile(Scope scope, Constraint left);
   Value get(Scope scope, T left);
}