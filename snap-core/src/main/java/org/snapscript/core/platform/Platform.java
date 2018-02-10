package org.snapscript.core.platform;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.snapscript.core.Type;
import org.snapscript.core.function.Invocation;

public interface Platform {
   Invocation createShellConstructor(Type type);
   Invocation createSuperConstructor(Type type, Type base);
   Invocation createSuperMethod(Type type, Method method);
   Invocation createConstructor(Type type, Constructor constructor);
   Invocation createMethod(Type type, Method method);
}