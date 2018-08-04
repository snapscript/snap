package org.snapscript.tree.operation;

import static org.snapscript.core.variable.Value.NULL;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.StringBuilder;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
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
   public void define(Scope scope) throws Exception {
      left.define(scope);
      right.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint context) throws Exception {
      left.compile(scope, null);
      return right.compile(scope, null);
   }
   
   
   @Override
   public Value evaluate(Scope scope, Value context) throws Exception {
      Value leftResult = left.evaluate(scope, NULL);
      Value rightResult = right.evaluate(scope, NULL);
      
      if(operator == NumericOperator.PLUS) {
         Object leftValue = leftResult.getValue();
         Object rightValue = rightResult.getValue();
         
         if(!NumericChecker.isBothNumeric(leftValue, rightValue)) {
            String leftText = StringBuilder.create(scope, leftValue);
            String rightText = StringBuilder.create(scope, rightValue);            
            String text = leftText.concat(rightText);
            Module module = scope.getModule();
            
            return Value.getTransient(text, module);
         }
      }
      return operator.operate(scope, leftResult, rightResult);
   }
}