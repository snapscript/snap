package org.snapscript.core.attribute;

import java.util.List;

import org.snapscript.core.Handle;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.type.Type;

public interface Attribute extends Handle {
   String getName();
   Type getSource(); // declaring type
   List<Constraint> getGenerics();
   Constraint getConstraint();
   int getModifiers();
}