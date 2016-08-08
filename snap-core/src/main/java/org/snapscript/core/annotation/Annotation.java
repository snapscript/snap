package org.snapscript.core.annotation;

import org.snapscript.core.Any;

public interface Annotation extends Any{
   Object getAttribute(String name);
   String getName();
}
 