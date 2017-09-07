package org.snapscript.core.define;

import org.snapscript.core.Scope;
import org.snapscript.core.platform.Bridge;

public interface Instance extends Scope {
   Instance getStack();
   Instance getScope();
   Instance getSuper();
   Bridge getBridge();
}