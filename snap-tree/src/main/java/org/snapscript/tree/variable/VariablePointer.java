package org.snapscript.tree.variable;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;

public interface VariablePointer<T> {
   Constraint check(Scope scope, Constraint left);
   Value get(Scope scope, T left);
}