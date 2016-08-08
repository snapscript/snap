package org.snapscript.core;

public class NoStatement extends Statement {
   
   @Override
   public Result execute(Scope scope) throws Exception {
      return ResultType.getNormal();
   }
}
