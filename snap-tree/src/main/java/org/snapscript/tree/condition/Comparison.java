package org.snapscript.tree.condition;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.parse.StringToken;

public class Comparison extends Evaluation {   
   
   private final RelationalOperator operator;
   private final Evaluation left;
   private final Evaluation right;
   
   public Comparison(Evaluation left) {
      this(left, null, null);
   }
   
   public Comparison(Evaluation left, StringToken operator, Evaluation right) {
      this.operator = RelationalOperator.resolveOperator(operator);
      this.left = left;
      this.right = right;
   }

   @Override
   public Value compile(Scope scope, Object context) throws Exception {
      if(right != null) {
         right.compile(scope, null);
      }
      return left.compile(scope, null);
   }  
   
   @Override
   public Value evaluate(Scope scope, Object context) throws Exception {
      if(right != null) {
         Value leftResult = left.evaluate(scope, null);
         Value rightResult = right.evaluate(scope, null);
         
         return operator.operate(scope, leftResult, rightResult);
      }
      return left.evaluate(scope, null);
   }      
}