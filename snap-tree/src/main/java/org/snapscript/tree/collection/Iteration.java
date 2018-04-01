package org.snapscript.tree.collection;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public interface Iteration {
   Type getEntry(Scope scope) throws Exception;
   Iterable getIterable(Scope scope) throws Exception;
}