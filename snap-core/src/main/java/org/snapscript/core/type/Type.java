package org.snapscript.core.type;

import java.util.List;

import org.snapscript.common.Progress;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.module.Module;
import org.snapscript.core.property.Property;
import org.snapscript.core.scope.Scope;

public interface Type extends Any {
   Progress<Phase> getProgress();
   List<Constraint> getConstraints();
   List<Annotation> getAnnotations();
   List<Property> getProperties();
   List<Function> getFunctions();
   List<Constraint> getTypes();
   Category getCategory();
   Module getModule();
   Scope getScope();
   Class getType();
   Type getOuter();
   Type getEntry();
   String getName();
   int getOrder();
}