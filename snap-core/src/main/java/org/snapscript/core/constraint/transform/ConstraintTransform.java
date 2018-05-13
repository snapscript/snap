package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public interface ConstraintTransform {
   ConstraintRule apply(Constraint constraint);
}
