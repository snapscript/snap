package org.snapscript.core.define;

import org.snapscript.core.Scope;

public interface Instance extends Scope {   
   Instance getInner();
   Instance getObject();
   Instance getSuper();
}
