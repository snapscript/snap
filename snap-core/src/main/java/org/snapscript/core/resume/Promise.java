package org.snapscript.core.resume;

import java.util.concurrent.TimeUnit;

public interface Promise<T> {
   T get();
   T get(long wait);
   T get(long wait, TimeUnit unit);
   Promise<T> join();
   Promise<T> join(long wait);
   Promise<T> join(long wait, TimeUnit unit);
   Promise<T> success(Task<T> task);
   Promise<T> success(Runnable task);
   Promise<T> failure(Task<Throwable> task);
   Promise<T> failure(Runnable task);
}
