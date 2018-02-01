package org.snapscript.core.dispatch;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public interface CallDispatcher<T> {
   Value dispatch(Scope scope, T object, Object... arguments) throws Exception;
}