package org.snapscript.core.function;

import java.util.List;

import org.snapscript.core.Constraint;
import org.snapscript.core.Handle;
import org.snapscript.core.Type;
import org.snapscript.core.annotation.Annotation;

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