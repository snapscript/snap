package org.snapscript.core.function;

import org.snapscript.core.Bug;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;

public interface Invocation<T> {
   @Bug("why do we return Result here the type Result.getThrows() is redundant..")
   Result invoke(Scope scope, T object, Object... list) throws Exception;
}