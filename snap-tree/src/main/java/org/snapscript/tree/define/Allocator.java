package org.snapscript.tree.define;

import org.snapscript.core.Scope;
import org.snapscript.core.define.Instance;

public interface Allocator {
   Instance allocate(Scope scope, Instance base, Object... list) throws Exception;
}
