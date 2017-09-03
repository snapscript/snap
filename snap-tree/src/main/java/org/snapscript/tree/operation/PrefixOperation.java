package org.snapscript.tree.operation;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.parse.StringToken;

public class PrefixOperation extends Evaluation {
   
   private final PrefixOperator operator;
   private final Evaluation evaluation;
   
   public PrefixOperation(StringToken operator, Evaluation evaluation) {
      this.operator = PrefixOperator.resolveOperator(operator);
      this.evaluation = evaluation;
   }
   
   @Override
   public Value compile(Scope scope, Object context) throws Exception {
      evaluation.compile(scope, null);
      return ValueType.getTransient(null);
   }
   
   @Override
   public Value evaluate(Scope scope, Object context) throws Exception {
      Value rightResult = evaluation.evaluate(scope, null);      
      return operator.operate(rightResult);
   } 
}