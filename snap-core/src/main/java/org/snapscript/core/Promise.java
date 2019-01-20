package org.snapscript.core;

import org.snapscript.common.Consumer;

public interface Promise<T> {
   Object get();
   Object get(long wait);
   Promise<T> then(Consumer<T, Object> task);
}
