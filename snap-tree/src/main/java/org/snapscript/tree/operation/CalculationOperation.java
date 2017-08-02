package org.snapscript.tree.operation;

import org.snapscript.core.convert.StringBuilder;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;

public class CalculationOperation implements Evaluation {

   private final NumericOperator operator;
   private final Evaluation left;
   private final Evaluation right;
   
   public CalculationOperation(NumericOperator operator, Evaluation left, Evaluation right) {
      this.operator = operator;
      this.left = left;
      this.right = right;
   }
   
   @Override
   public Value evaluate(Scope scope, Object context) throws Exception {
      Value leftResult = left.evaluate(scope, null);
      Value rightResult = right.evaluate(scope, null);
      
      if(operator == NumericOperator.PLUS) {
         Object leftValue = leftResult.getValue();
         Object rightValue = rightResult.getValue();
         
         if(!NumericChecker.bothNumeric(leftValue, rightValue)) {
            String leftText = StringBuilder.create(scope, leftValue);
            String rightText = StringBuilder.create(scope, rightValue);            
            String text = leftText.concat(rightText);
            
            return ValueType.getTransient(text);
         }
      }
      return operator.operate(leftResult, rightResult);
   }
}