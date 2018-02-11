package org.snapscript.tree.variable;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;

public interface VariablePointer<T> {
   Type check(Scope scope, Type left);
   Value get(Scope scope, T left);
}