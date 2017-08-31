package org.snapscript.core.convert;

import java.lang.reflect.InvocationHandler;

import org.snapscript.core.Bug;

@Bug("every proxy should implement Handle.getHandle so that we do not cache the proxy")
public interface ProxyHandler extends InvocationHandler {
   Object extract();
}