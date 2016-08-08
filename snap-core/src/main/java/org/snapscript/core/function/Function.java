package org.snapscript.core.function;

import java.util.List;

import org.snapscript.core.Any;
import org.snapscript.core.Type;
import org.snapscript.core.annotation.Annotation;

public interface Function<T> extends Any {
   int getModifiers();
   Type getType();
   Type getDefinition();
   Type getConstraint();
   String getName();
   Signature getSignature();
   List<Annotation> getAnnotations();
   Invocation<T> getInvocation();
   String getDescription();
}
