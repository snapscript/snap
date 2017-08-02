package org.snapscript.core.function;

import java.util.List;

import org.snapscript.core.Handle;
import org.snapscript.core.Type;
import org.snapscript.core.annotation.Annotation;

public interface Function extends Handle {
   int getModifiers();
   Type getType();
   Type getConstraint();
   String getName();
   Signature getSignature();
   List<Annotation> getAnnotations();
   Invocation getInvocation();
   String getDescription();
}