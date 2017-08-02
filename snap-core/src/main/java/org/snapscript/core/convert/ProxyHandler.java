package org.snapscript.core.convert;

import java.lang.reflect.InvocationHandler;

public interface ProxyHandler extends InvocationHandler {
   Object extract();
}