package org.snapscript.core;

import org.snapscript.core.result.Result;

public class NoExecution extends Execution {
   
   private final Result result;
   
   public NoExecution(Result result) {
      this.result = result;
   }

   @Override
   public Result execute(Scope scope) throws Exception {     
      return result;
   }
}
