package org.snapscript.core.scope.instance;

import org.snapscript.core.platform.Bridge;
import org.snapscript.core.scope.Scope;

public interface Instance extends Scope {
   Instance getStack();
   Instance getScope();
   Instance getSuper();
   Bridge getBridge();
   Object getProxy();
}