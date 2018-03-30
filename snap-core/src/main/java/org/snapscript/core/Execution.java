package org.snapscript.core;

import org.snapscript.core.result.Result;

public abstract class Execution {
   public abstract Result execute(Scope scope) throws Exception;
}
