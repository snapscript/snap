package org.snapscript.tree.collection;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;

public interface Iteration {
   Type getEntry(Scope scope) throws Exception;
   Iterable getIterable(Scope scope) throws Exception;
}
