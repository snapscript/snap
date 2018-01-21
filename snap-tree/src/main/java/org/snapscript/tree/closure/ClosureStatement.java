package org.snapscript.tree.closure;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Value;

public class ClosureStatement extends Statement {
   
   private final Evaluation evaluation;
   private final Statement statement;
   
   public ClosureStatement(Statement statement, Evaluation evaluation) {
      this.evaluation = evaluation;
      this.statement = statement;
   }
   
   @Override
   public void compile(Scope scope) throws Exception {   
      if(evaluation != null){
         evaluation.compile(scope);
      }
      if(statement != null) {
         statement.compile(scope);
      }
   }

   @Override
   public Result execute(Scope scope) throws Exception {
      if(evaluation != null) {
         Value value = evaluation.evaluate(scope, null);
         Object object = value.getValue();
         
         return Result.getNormal(object);
      }
      return statement.execute(scope);
   }
}