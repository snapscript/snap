package org.snapscript.core;

import static org.snapscript.core.result.Result.NORMAL;

@Bug("really???")
public class NoStatement extends Statement {

   @Override
   public Execution compile(Scope scope) throws Exception {
      return new NoExecution(NORMAL);
   }
   
   
}