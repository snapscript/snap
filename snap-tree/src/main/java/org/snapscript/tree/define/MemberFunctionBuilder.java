package org.snapscript.tree.define;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.function.Function;

public interface MemberFunctionBuilder {
   Function create(Scope scope, Initializer initializer, Type type);
}
