package org.snapscript.core.variable;

import org.snapscript.core.type.Type;

public interface Data {
   Type getType();
   <T> T getValue();
}
