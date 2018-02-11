package org.snapscript.core.dispatch;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;

public interface CallDispatcher<T> {
   Type validate(Scope scope, Type object, Type... arguments) throws Exception;
   Value dispatch(Scope scope, T object, Object... arguments) throws Exception;
}