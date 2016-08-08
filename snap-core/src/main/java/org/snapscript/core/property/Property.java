package org.snapscript.core.property;

import java.util.List;

import org.snapscript.core.Any;
import org.snapscript.core.Type;
import org.snapscript.core.annotation.Annotation;

public interface Property<T> extends Any {
   List<Annotation> getAnnotations();
   Type getType(); // declaring type
   Type getConstraint();
   String getName();
   int getModifiers();
   Object getValue(T source);
   void setValue(T source, Object value);
}
