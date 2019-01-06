package org.snapscript.core.function.index;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.type.Type;

public interface ReturnType {
   Constraint check(Constraint left, Type[] types) throws Exception;
}
