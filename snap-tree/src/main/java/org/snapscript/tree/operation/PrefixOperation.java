package org.snapscript.tree.operation;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.parse.StringToken;

public class PrefixOperation implements Evaluation {
   
   private final PrefixOperator operator;
   private final Evaluation right;
   
   public PrefixOperation(StringToken operator, Evaluation right) {
      this.operator = PrefixOperator.resolveOperator(operator);
      this.right = right;
   }
   
   @Override
   public Value evaluate(Scope scope, Object context) throws Exception {
      Value rightResult = right.evaluate(scope, null);      
      return operator.operate(rightResult);
   } 
}
