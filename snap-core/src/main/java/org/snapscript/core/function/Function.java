package org.snapscript.core.function;

import java.util.List;

import org.snapscript.core.type.Handle;
import org.snapscript.core.type.Type;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.constraint.Constraint;

public interface Function extends Handle {
   int getModifiers();
   Type getType(); // declared class
   String getName();
   Signature getSignature();
   Constraint getConstraint();
   List<Annotation> getAnnotations();
   Invocation getInvocation();
   String getDescription();
   Object getProxy(Class type);
   Object getProxy();
}