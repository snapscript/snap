package org.snapscript.core.type;

import java.util.List;

import org.snapscript.core.Entity;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.module.Module;
import org.snapscript.core.property.Property;

public interface Type extends Entity {
   List<Constraint> getGenerics();
   List<Annotation> getAnnotations();
   List<Property> getProperties();
   List<Function> getFunctions();
   List<Constraint> getTypes();
   Module getModule();
   Class getType();
   Type getOuter();
   Type getEntry();
}