package org.snapscript.core;

public class NoExecution extends Execution {
   
   private final Result result;
   
   public NoExecution(ResultType type) {
      this.result = new Result(type);
   }

   @Override
   public Result execute(Scope scope) throws Exception {     
      return result;
   }
}
