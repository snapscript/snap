package org.snapscript.core.scope;

import org.snapscript.core.type.Any;

public interface Model extends Any {
   Object getAttribute(String name);
}