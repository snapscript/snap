package org.snapscript.core.attribute;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public interface AttributeResult {
   Constraint getConstraint(Scope scope, Constraint left, Type... types) throws Exception;
}
