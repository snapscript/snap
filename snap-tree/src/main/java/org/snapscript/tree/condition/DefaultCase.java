package org.snapscript.tree.condition;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Statement;
import org.snapscript.tree.CompoundStatement;

public class DefaultCase implements Case {

   private final Statement statement;
   
   public DefaultCase(Statement... statements) {
      this.statement = new CompoundStatement(statements);
   }
   
   @Override
   public Evaluation getEvaluation(){
      return null;
   }
   
   @Override
   public Statement getStatement(){
      return statement;
   }
}
