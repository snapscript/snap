package org.snapscript.core.function;

import org.snapscript.core.Scope;

public interface InvocationBuilder {
   Invocation create(Scope scope) throws Exception;
}