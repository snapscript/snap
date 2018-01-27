package org.snapscript.core;

import java.util.List;

import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.function.Function;
import org.snapscript.core.property.Property;

public interface Entity extends Any{
   List<Annotation> getAnnotations();
   List<Property> getProperties();
   List<Function> getFunctions();
   String getName();
}
