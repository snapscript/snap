package org.snapscript.core.resume;

import org.snapscript.core.scope.Scope;

public interface TaskScheduler {
   Promise schedule(Scope scope, Task<Answer> task);
}
