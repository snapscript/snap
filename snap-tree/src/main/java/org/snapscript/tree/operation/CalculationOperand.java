package org.snapscript.tree.operation;

import org.snapscript.core.Evaluation;

public class CalculationOperand implements CalculationPart {   
   
   private final Evaluation evaluation;
   
   public CalculationOperand(Evaluation evaluation) {
      this.evaluation = evaluation;
   }
   
   @Override
   public Evaluation getEvaluation(){
      return evaluation;
   }
   
   @Override
   public NumericOperator getOperator(){
      return null;
   }
}