package org.snapscript.core.function;

import org.snapscript.core.scope.Scope;

public interface Connection<T>  {
   boolean match(Scope scope, Object object, Object... arguments) throws Exception;
   Object invoke(Scope scope, T object, Object... list) throws Exception;
}
