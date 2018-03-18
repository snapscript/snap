package org.snapscript.tree.variable;

import org.snapscript.core.Constraint;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public interface VariablePointer<T> {
   Constraint check(Scope scope, Constraint left);
   Value get(Scope scope, T left);
}