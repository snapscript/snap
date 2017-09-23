package org.snapscript.core.index;

public interface MethodAdapter {
   Object invoke(Object object, Object... list) throws Exception;
}