package org.snapscript.core;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Any;

public interface Entity extends Any{
   Scope getScope();
}
