package org.snapscript.core.type.index;

public interface MethodAdapter {
   Object invoke(Object object, Object... list) throws Exception;
}