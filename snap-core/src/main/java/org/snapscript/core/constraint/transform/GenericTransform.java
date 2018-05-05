package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public interface GenericTransform {
   GenericReference getReference(Constraint constraint);
}
