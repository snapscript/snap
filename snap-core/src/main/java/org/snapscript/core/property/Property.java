package org.snapscript.core.property;

import java.util.List;

import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.attribute.Attribute;

public interface Property<T> extends Attribute {
   String getAlias();
   List<Annotation> getAnnotations();
   Object getValue(T source);
   void setValue(T source, Object value);
}