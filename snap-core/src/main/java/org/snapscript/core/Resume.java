package org.snapscript.core;

public interface Resume<A, B> {
   Result resume(Scope scope, A value) throws Exception;
   Resume suspend(Result result, Resume resume, B value) throws Exception;
}