package org.snapscript.tree.variable;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public interface VariablePointer<T> {
   Value get(Scope scope, T left);
}