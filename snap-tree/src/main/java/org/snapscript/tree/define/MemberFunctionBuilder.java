package org.snapscript.tree.define;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.function.FunctionBody;
import org.snapscript.core.type.TypeBody;

public interface MemberFunctionBuilder {
   FunctionBody create(TypeBody body, Scope scope, Type type);
}