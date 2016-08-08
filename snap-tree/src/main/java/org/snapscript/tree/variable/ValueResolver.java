package org.snapscript.tree.variable;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public interface ValueResolver<T> {
   Value resolve(Scope scope, T left);
}