package org.snapscript.tree.condition;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Statement;

public class DefaultCase implements Case {

   private final Statement statement;
   
   public DefaultCase(Statement statement) {
      this.statement = statement;
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
