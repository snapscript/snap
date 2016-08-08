package org.snapscript.core.function;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;

public interface Invocation<T> {
   Result invoke(Scope scope, T object, Object... list) throws Exception;
}
