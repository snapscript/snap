package org.snapscript.core.function.dispatch;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
                   
public interface FunctionDispatcher<T> {
   Constraint compile(Scope scope, Constraint object, Type... arguments) throws Exception;
   Value dispatch(Scope scope, T object, Object... arguments) throws Exception;
}