package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;

public interface ConstraintRule {
   Constraint getResult(Scope scope, Constraint returns);
   Constraint getSource();
}
