package org.snapscript.core;

import org.snapscript.common.Consumer;

public interface Promise<T> {
   Object get();
   Object get(long wait);
   Promise<T>  block();
   Promise<T>  block(long wait);
   Promise<T> then(Consumer<T, Object> task);
   Promise<T> fail(Consumer<Throwable, Object> task);
}
