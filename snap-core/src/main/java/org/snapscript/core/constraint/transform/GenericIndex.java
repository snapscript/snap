package org.snapscript.core.constraint.transform;

import org.snapscript.core.constraint.Constraint;

public interface GenericIndex {
   Constraint getType(Constraint constraint, String name);
}
