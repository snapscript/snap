package org.snapscript.core.attribute;

import org.snapscript.core.Handle;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.type.Type;

public interface Attribute extends Handle {
   String getName();
   Type getSource(); // declaring type
   Constraint getConstraint();
   int getModifiers();
}