package org.snapscript.tree;

import static org.snapscript.core.result.Result.NORMAL;

import org.snapscript.core.Execution;
import org.snapscript.core.NoExecution;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.parse.StringToken;

public class EmptyStatement extends Statement {
   
   private final Execution execution;
   
   public EmptyStatement() {
      this(null);
   }
   
   public EmptyStatement(StringToken token) {
      this.execution = new NoExecution(NORMAL);
   }

   @Override
   public Execution compile(Scope scope) throws Exception {
      return execution;
   }

}