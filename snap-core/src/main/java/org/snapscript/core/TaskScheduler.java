package org.snapscript.core;

public interface TaskScheduler {
   Promise schedule(Task<Answer> task);
}
