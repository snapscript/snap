package org.snapscript.tree.define;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.instance.Instance;

public interface TypeAllocator {
   Instance allocate(Scope scope, Instance base, Object... list) throws Exception;
}