package org.snapscript.tree.operation;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
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
   protected Value create(Scope scope) throws Exception {
      Value value = literal.evaluate(scope, null);
      Number number = value.getValue();
      
      if(number == null) {
         throw new InternalStateException("Number value was null");
      }
      Value result = operator.operate(number);
      Object signed = result.getValue();
      Type constraint = value.getType(scope);
      
      return Value.getTransient(signed, constraint);
   }
}