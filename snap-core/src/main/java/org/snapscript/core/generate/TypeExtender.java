package org.snapscript.core.generate;

import java.lang.reflect.Method;

import org.snapscript.core.Bug;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Instance;
import org.snapscript.core.function.Invocation;

@Bug("this probably has the wrong name!! Maybe CodeGenerator??")
public interface TypeExtender {
   Instance createInstance(Scope scope, Type real, Object... args);
   Invocation createSuper(Scope scope, Class proxy, Method method);
}