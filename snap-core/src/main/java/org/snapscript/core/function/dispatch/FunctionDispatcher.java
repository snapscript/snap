package org.snapscript.core.function.dispatch;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
                   
public interface FunctionDispatcher<T> {
   Constraint compile(Scope scope, Type object, Type... arguments) throws Exception;
   Value dispatch(Scope scope, T object, Object... arguments) throws Exception;
}