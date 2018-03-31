package org.snapscript.tree.condition;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;

public class Choice extends Evaluation {
   
   private final Evaluation condition;
   private final Evaluation positive;
   private final Evaluation negative;
   
   public Choice(Evaluation condition, Evaluation positive, Evaluation negative) {
      this.condition = condition;
      this.positive = positive;
      this.negative = negative;
   }

   @Override
   public void define(Scope scope) throws Exception {
      condition.define(scope);
      positive.define(scope);
      negative.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      condition.define(scope);     
      negative.define(scope);
      
      return positive.compile(scope, left);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Value result = condition.evaluate(scope, null);
      boolean value = result.getBoolean();
      
      if(value) {
         return positive.evaluate(scope, left);
      } 
      return negative.evaluate(scope, left);
   }
}