package org.snapscript.core;

import static org.snapscript.core.result.Result.NORMAL;

import org.snapscript.core.scope.Scope;

public class NoStatement extends Statement {
   
   private final Execution execution;
   
   public NoStatement() {
      this.execution = new NoExecution(NORMAL);
   }

   @Override
   public Execution compile(Scope scope) throws Exception {
      return execution;
   }
   
   
}