package org.snapscript.tree.operation;

import static org.snapscript.core.ModifierType.CONSTANT;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.parse.StringToken;
import org.snapscript.tree.literal.Literal;
import org.snapscript.tree.literal.NumberLiteral;

public class SignedNumber extends Literal {
   
   private SignOperator operator;
   private NumberLiteral literal;
   
   public SignedNumber(NumberLiteral literal) {
      this(null, literal);
   }
   
   public SignedNumber(StringToken sign, NumberLiteral literal) {
      this.operator = SignOperator.resolveOperator(sign);
      this.literal = literal;
   }

   @Override
   protected LiteralValue create(Scope scope) throws Exception {
      Value value = literal.evaluate(scope, null);
      Number number = value.getValue();
      
      if(number == null) {
         throw new InternalStateException("Number value was null");
      }
      Value result = operator.operate(number);
      Number signed = result.getValue();
      Class real = signed.getClass();
      Constraint constraint = Constraint.getConstraint(real, CONSTANT.mask);
      
      return new LiteralValue(signed, constraint);
   }
}