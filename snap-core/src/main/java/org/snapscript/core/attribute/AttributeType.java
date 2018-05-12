package org.snapscript.core.attribute;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;

public interface AttributeType {
   Constraint getConstraint(Scope scope, Constraint left);
}
