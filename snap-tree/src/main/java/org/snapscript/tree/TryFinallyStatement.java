package org.snapscript.tree;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class TryFinallyStatement extends Statement {
   
   private final Statement statement;
   private final Statement finish;
   
   public TryFinallyStatement(Statement statement, Statement finish) {
      this.statement = statement;
      this.finish = finish;
   }   
   
   @Override
   public Result compile(Scope scope) throws Exception {  
      if(finish != null) {
         finish.compile(scope);
      }
      return statement.compile(scope);
   }

   @Override
   public Result execute(Scope scope) throws Exception {
      try {
         return statement.execute(scope);
      } finally {
         finish.execute(scope);         
      }
   }
}
