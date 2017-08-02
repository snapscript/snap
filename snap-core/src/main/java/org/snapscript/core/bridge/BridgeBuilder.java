package org.snapscript.core.bridge;

import java.lang.reflect.Method;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Instance;
import org.snapscript.core.function.Invocation;

public interface BridgeBuilder {
   Instance superInstance(Scope scope, Type real, Object... list);
   Invocation superInvocation(Scope scope, Class proxy, Method method); 
}