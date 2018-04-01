package org.snapscript.core;

import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;

public abstract class Execution {
   public abstract Result execute(Scope scope) throws Exception;
}
