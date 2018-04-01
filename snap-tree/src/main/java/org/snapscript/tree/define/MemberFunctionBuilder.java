package org.snapscript.tree.define;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeBody;
import org.snapscript.core.function.FunctionHandle;

public interface MemberFunctionBuilder {
   FunctionHandle create(TypeBody body, Scope scope, Type type);
}