package org.snapscript.core.type.annotation;

import org.snapscript.core.type.Any;

public interface Annotation extends Any{
   Object getAttribute(String name);
   String getName();
}