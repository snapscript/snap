package org.snapscript.core.attribute;

import org.snapscript.core.Handle;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.type.Type;

public interface Attribute extends Handle {
   Type getType(); // declaring type
   String getName();
   Constraint getConstraint();
   int getModifiers();
}