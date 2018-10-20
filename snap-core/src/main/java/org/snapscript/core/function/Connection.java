package org.snapscript.core.function;

import org.snapscript.core.scope.Scope;

public interface Connection<T> extends Invocation<T> {
   boolean match(Scope scope, Object object, Object... arguments) throws Exception;
}
