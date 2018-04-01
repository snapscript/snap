package org.snapscript.core.function;

import org.snapscript.core.scope.Scope;

public interface Invocation<T> {
   Object invoke(Scope scope, T object, Object... list) throws Exception;
}