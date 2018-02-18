package org.snapscript.core;

import org.snapscript.core.local.Local;

public interface Table extends Iterable<Local> {
   Local get(int index);
   void add(int index, Local local);
}