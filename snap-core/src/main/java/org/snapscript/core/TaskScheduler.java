package org.snapscript.core;

import java.util.concurrent.Callable;

public interface TaskScheduler {
   <T> Promise<T> schedule(Callable<T> task);
}
