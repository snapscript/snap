package org.snapscript.tree.function;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public interface VariableMatcher {
   Value compile(Scope scope) throws Exception;
   Value execute(Scope scope) throws Exception;
}
