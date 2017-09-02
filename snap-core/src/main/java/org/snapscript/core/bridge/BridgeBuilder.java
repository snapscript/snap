package org.snapscript.core.bridge;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.snapscript.core.Type;
import org.snapscript.core.function.Invocation;

public interface BridgeBuilder {
   Invocation superConstructor(Type type, Type base);
   Invocation superMethod(Type type, Method method);
   Invocation thisConstructor(Type type, Constructor constructor);
   Invocation thisMethod(Type type, Method method);
}