package org.snapscript.tree.collection;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Data;

public interface Iteration {
   Type getEntry(Scope scope) throws Exception;
   Iterable<Data> getIterable(Scope scope) throws Exception;
}