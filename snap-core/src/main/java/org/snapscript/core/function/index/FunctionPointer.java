package org.snapscript.core.function.index;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.scope.Scope;

public interface FunctionPointer {
   Constraint getConstraint(Scope scope, Constraint left);
   Function getFunction();
   Invocation getInvocation();  
}