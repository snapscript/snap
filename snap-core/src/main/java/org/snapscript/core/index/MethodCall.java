package org.snapscript.core.index;

public interface MethodCall<T> {
   Object call(T object, Object[] arguments) throws Exception;
}
