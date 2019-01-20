package org.snapscript.core;

import org.snapscript.common.Consumer;

public interface TaskScheduler {
   <T> Promise<T> schedule(Consumer<Object, T> consumer);
}
