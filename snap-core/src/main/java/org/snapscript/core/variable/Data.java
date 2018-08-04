package org.snapscript.core.variable;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public interface Data {
   Type getType(Scope scope);
   <T> T getValue();
}
