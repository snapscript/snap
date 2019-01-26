package org.snapscript.core.resume;

public interface TaskScheduler {
   Promise schedule(Task<Answer> task);
}
