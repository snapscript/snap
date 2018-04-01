package org.snapscript.core.scope.index;

import org.snapscript.core.scope.index.Local;

public interface Table extends Iterable<Local> {
   Local get(int index);
   void add(int index, Local local);
}
