package org.snapscript.core;

import org.snapscript.core.scope.Scope;

public interface Entity extends Any{
   Scope getScope();
   String getName();
   int getModifiers();
   int getOrder();
}
