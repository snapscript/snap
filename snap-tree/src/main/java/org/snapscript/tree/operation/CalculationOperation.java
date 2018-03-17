package org.snapscript.tree.operation;

import org.snapscript.core.Bug;
import org.snapscript.core.Constraint;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.convert.StringBuilder;
import org.snapscript.tree.math.NumericChecker;
import org.snapscript.tree.math.NumericOperator;

public class CalculationOperation extends Evaluation {

   private final NumericOperator operator;
   private final Evaluation left;
   private final Evaluation right;
   
   public CalculationOperation(NumericOperator operator, Evaluation left, Evaluation right) {
      this.operator = operator;
      this.left = left;
      this.right = right;
   }
   
   @Override
   public void compile(Scope scope) throws Exception {
      left.compile(scope);
      right.compile(scope);
   }
   
   @Bug("not correct")
   @Override
   public Constraint validate(Scope scope, Constraint left) throws Exception {
      return right.validate(scope, left);
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
            
            return Value.getTransient(text);
         }
      }
      return operator.operate(leftResult, rightResult);
   }
}