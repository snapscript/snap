package org.snapscript.tree.closure;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Execution;
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
   public void define(Scope scope) throws Exception {   
      if(evaluation != null){
         evaluation.define(scope);
      }
      if(statement != null) {
         statement.define(scope);
      }
   }
   
   @Override
   public Execution compile(Scope scope) throws Exception {
      Execution execution = statement.compile(scope);
      evaluation.compile(scope, null);
      return new ClosureExecution(execution, evaluation);
   }
   
   private static class ClosureExecution extends Execution {
      
      private final Evaluation evaluation;
      private final Execution statement;
      
      public ClosureExecution(Execution statement, Evaluation evaluation) {
         this.evaluation = evaluation;
         this.statement = statement;
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
}