package org.snapscript.tree.closure;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
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
   public Result compile(Scope scope) throws Exception {   
      if(statement != null) {
         statement.compile(scope);
      }
      return ResultType.getNormal();
   }

   @Override
   public Result execute(Scope scope) throws Exception {
      if(evaluation != null) {
         Value value = evaluation.evaluate(scope, null);
         Object object = value.getValue();
         
         return ResultType.getNormal(object);
      }
      return statement.execute(scope);
   }
}
