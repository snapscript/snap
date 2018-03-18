package org.snapscript.core.function;

import org.snapscript.core.Scope;

public interface InvocationBuilder {
   Invocation define(Scope scope) throws Exception;
}