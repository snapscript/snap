package org.snapscript.core.constraint.transform;

import java.util.List;

import org.snapscript.core.constraint.Constraint;

public interface ConstraintSource {
   List<Constraint> getConstraints(Constraint constraint);
}
