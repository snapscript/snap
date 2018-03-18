package org.snapscript.core;

import static org.snapscript.core.ResultType.NORMAL;

public abstract class Execution {
   
   public static Execution getNone() {
      return new NoExecution(NORMAL);
   }
   
   public abstract Result execute(Scope scope) throws Exception;
}
