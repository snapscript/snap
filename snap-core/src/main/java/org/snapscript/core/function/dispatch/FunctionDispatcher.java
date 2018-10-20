package org.snapscript.core.function.dispatch;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Connection;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
                   
public interface FunctionDispatcher {
   Constraint compile(Scope scope, Constraint constraint, Type... arguments) throws Exception;
   Connection connect(Scope scope, Value value, Object... arguments) throws Exception;
}