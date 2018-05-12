package org.snapscript.core.function;

import java.util.List;

import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.attribute.Attribute;

public interface Function extends Attribute {
   Signature getSignature();
   List<Annotation> getAnnotations();
   Invocation getInvocation();
   String getDescription();
   Object getProxy(Class type);
   Object getProxy();
}