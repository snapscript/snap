package org.snapscript.core.property;

import java.util.List;

import org.snapscript.core.type.Any;
import org.snapscript.core.type.Type;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.constraint.Constraint;

public interface Property<T> extends Any {
   Type getType(); // declaring type
   String getName();
   int getModifiers();
   List<Annotation> getAnnotations();
   Constraint getConstraint();
   Object getValue(T source);
   void setValue(T source, Object value);
}