package org.snapscript.tree.condition;

import org.snapscript.core.BooleanValue;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.parse.StringToken;

public class Combination implements Evaluation {
   
   private final ConditionalOperator operator;
   private final Evaluation right;
   private final Evaluation left;
   
   public Combination(Evaluation left) {
      this(left, null, null);
   }
   
   public Combination(Evaluation left, StringToken operator, Evaluation right) {
      this.operator = ConditionalOperator.resolveOperator(operator);
      this.right = right;
      this.left = left;
   }
   
   @Override
   public Value evaluate(Scope scope, Object context) throws Exception { 
      Value first = evaluate(scope, left);
      
      if(first == BooleanValue.TRUE) {
         if(operator != null) {
            if(operator.isAnd()) {
               return evaluate(scope, right);
            }
         }
      } else {
         if(operator != null) {
            if(operator.isOr()) {
               return evaluate(scope, right);
            }
         }
      }
      return first;
   }
   
   private Value evaluate(Scope scope, Evaluation evaluation) throws Exception { 
      Value value = evaluation.evaluate(scope, null);
      Boolean result = value.getBoolean();
      
      if(result.booleanValue()) {
         return BooleanValue.TRUE;
      }
      return BooleanValue.FALSE;
   } 
}