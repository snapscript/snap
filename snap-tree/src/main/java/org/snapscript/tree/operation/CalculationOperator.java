package org.snapscript.tree.operation;

import static org.snapscript.tree.math.NumericOperator.COALESCE;

import org.snapscript.core.Evaluation;
import org.snapscript.parse.StringToken;
import org.snapscript.tree.condition.NullCoalesce;
import org.snapscript.tree.math.NumericOperator;

public class CalculationOperator implements CalculationPart {   
   
   private final NumericOperator operator;
   
   public CalculationOperator(StringToken operator) {
      this.operator = NumericOperator.resolveOperator(operator);
   }
   
   @Override
   public Evaluation getEvaluation(Evaluation left, Evaluation right){
      if(operator == COALESCE) {
         return new NullCoalesce(left, right);
      }
      return new CalculationOperation(operator, left, right);
   }
   
   @Override
   public NumericOperator getOperator(){
      return operator;
   }
}