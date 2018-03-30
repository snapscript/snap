package org.snapscript.core.yield;

import org.snapscript.core.Scope;
import org.snapscript.core.result.Result;

public interface Resume<A, B> {
   Result resume(Scope scope, A value) throws Exception;
   Resume suspend(Result result, Resume resume, B value) throws Exception;
}