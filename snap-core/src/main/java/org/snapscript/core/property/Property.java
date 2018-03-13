package org.snapscript.core.property;

import java.util.List;

import org.snapscript.core.Any;
import org.snapscript.core.Constraint;
import org.snapscript.core.Type;
import org.snapscript.core.annotation.Annotation;

public interface Property<T> extends Any {
   Type getType(); // declaring type
   String getName();
   int getModifiers();
   List<Annotation> getAnnotations();
   Constraint getConstraint();
   Object getValue(T source);
   void setValue(T source, Object value);
}