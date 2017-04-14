
package org.snapscript.tree.define;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.function.Function;

public interface MemberFunctionBuilder {
   Function create(TypeFactory factory, Scope scope, Type type);
}
