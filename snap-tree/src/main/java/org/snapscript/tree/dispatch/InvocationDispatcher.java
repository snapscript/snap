package org.snapscript.tree.dispatch;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public interface InvocationDispatcher<T> {
   Value dispatch(Scope scope, T object, Object... arguments) throws Exception;
}