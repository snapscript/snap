package org.snapscript.core;

import org.snapscript.common.Consumer;

public interface Promise<T> {
   T get();
   T get(long wait);
   Promise<T> join();
   Promise<T> join(long wait);
   Promise<T> thenAccept(Consumer<T, Object> task);
   Promise<T> thenCatch(Consumer<Throwable, Object> task);
}
