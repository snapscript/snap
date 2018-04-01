package org.snapscript.core.yield;

import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;

public interface Resume<A, B> {
   Result resume(Scope scope, A value) throws Exception;
   Resume suspend(Result result, Resume resume, B value) throws Exception;
}