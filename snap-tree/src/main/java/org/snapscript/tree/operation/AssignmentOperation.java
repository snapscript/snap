package org.snapscript.tree.operation;

import org.snapscript.core.convert.StringBuilder;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.parse.StringToken;

public class AssignmentOperation {

   private final AssignmentOperator operator;

   public AssignmentOperation(StringToken operator) {
      this.operator = AssignmentOperator.resolveOperator(operator);
   }

   public Value operate(Scope scope, Value left, Value right) throws Exception {
      if(operator != AssignmentOperator.EQUAL) {
         Object leftValue = left.getValue();

         if(!Number.class.isInstance(leftValue)) {
            Object rightValue = right.getValue();

            if(operator != AssignmentOperator.PLUS_EQUAL) {
               throw new InternalStateException("Operator " + operator + " is illegal");
            }
            String leftText = StringBuilder.create(scope, leftValue);
            String rightText = StringBuilder.create(scope, rightValue);
            String text = leftText.concat(rightText);

            left.setValue(text);
            return left;
         }
      }
      return operator.operate(scope, left, right);
   }
}
