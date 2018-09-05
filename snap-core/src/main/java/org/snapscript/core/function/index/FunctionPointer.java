package org.snapscript.core.function.index;

import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.scope.Scope;

public interface FunctionPointer {
   ReturnType getType(Scope scope);
   Function getFunction();
   Invocation getInvocation();
   boolean isCachable();
}