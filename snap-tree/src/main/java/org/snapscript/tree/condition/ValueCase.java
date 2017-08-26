package org.snapscript.tree.condition;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Statement;
import org.snapscript.tree.StatementBlock;

public class ValueCase implements Case {

   private final Evaluation evaluation;
   private final Statement statement;
   
   public ValueCase(Evaluation evaluation, Statement... statements) {
      this.statement = new StatementBlock(statements);
      this.evaluation = evaluation;
   }
   
   @Override
   public Evaluation getEvaluation(){
      return evaluation;
   }
   
   @Override
   public Statement getStatement(){
      return statement;
   }
}