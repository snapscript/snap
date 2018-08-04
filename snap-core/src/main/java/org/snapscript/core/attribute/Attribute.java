package org.snapscript.core.attribute;

import org.snapscript.core.Entity;
import org.snapscript.core.Handle;
import org.snapscript.core.constraint.Constraint;

public interface Attribute extends Handle {
   <T extends Entity> T getSource(); // declaring type
   Constraint getConstraint();
   String getName();
   int getModifiers();
}