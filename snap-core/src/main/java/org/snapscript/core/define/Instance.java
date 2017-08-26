package org.snapscript.core.define;

import org.snapscript.core.Scope;
import org.snapscript.core.bridge.Bridge;

public interface Instance extends Scope {
   Instance getInner();
   Instance getOuter();
   Instance getSuper();
   Bridge getBridge();
}