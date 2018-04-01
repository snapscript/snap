package org.snapscript.core.function;

import org.snapscript.core.scope.Scope;

public interface InvocationBuilder {
   void define(Scope scope) throws Exception;
   void compile(Scope scope) throws Exception;
   Invocation create(Scope scope) throws Exception;
}