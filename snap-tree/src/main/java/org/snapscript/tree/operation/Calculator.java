package org.snapscript.tree.operation;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Evaluation;
import org.snapscript.tree.math.NumericOperator;

public class Calculator {
   
   private final CalculatorStack<CalculationPart> tokens;
   private final CalculatorStack<Evaluation> variables;      
   private final List<CalculationPart> order;
   
   public Calculator() {
      this.tokens = new CalculatorStack<CalculationPart>();      
      this.variables = new CalculatorStack<Evaluation>();
      this.order = new ArrayList<CalculationPart>();
   }
   
   public Evaluation create(){
      while(!tokens.isEmpty()) {
         CalculationPart top = tokens.pop();
         
         if(top != null) {
            order.add(top);
         }
      }      
      for(CalculationPart token : order) {
         NumericOperator operator = token.getOperator();
   
         if(operator != null) {
            Evaluation right = variables.pop();
            Evaluation left = variables.pop();
            
            if(left != null && right != null) {             
               Evaluation evaluation = new CalculationOperation(operator, left, right);
               
               variables.push(evaluation);
            }                          
         } else {
            Evaluation evaluation = token.getEvaluation();
            
            if(token != null) {
               variables.push(evaluation);
            }
         }               
      }
      return variables.pop();
   }
   
   public void update(CalculationPart part) {
      NumericOperator operator = part.getOperator();
      
      if(operator != null) {
         while(!tokens.isEmpty()) {
            CalculationPart top = tokens.pop();
            NumericOperator other = top.getOperator();
                                 
            if(other.priority < operator.priority) {
               tokens.push(top);
               break;
            } else {
               order.add(top);
            }            
         }
         tokens.push(part);
      } else {
         order.add(part);
      }
   }
}