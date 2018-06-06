package org.snapscript.tree.literal;

import static org.snapscript.core.constraint.Constraint.BYTE;
import static org.snapscript.core.constraint.Constraint.DOUBLE;
import static org.snapscript.core.constraint.Constraint.FLOAT;
import static org.snapscript.core.constraint.Constraint.INTEGER;
import static org.snapscript.core.constraint.Constraint.LONG;
import static org.snapscript.core.constraint.Constraint.NUMBER;
import static org.snapscript.core.constraint.Constraint.SHORT;

import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.parse.NumberToken;
import org.snapscript.parse.StringToken;
import org.snapscript.tree.operation.SignOperator;

public class NumberLiteral extends Literal {
   
   private final SignOperator operator;
   private final NumberToken token;

   public NumberLiteral(NumberToken token) {
      this(null, token);
   }
   
   public NumberLiteral(StringToken sign, NumberToken token) {
      this.operator = SignOperator.resolveOperator(sign);
      this.token = token;
   }

   @Override
   protected LiteralValue create(Scope scope) throws Exception {
      Number number = token.getValue();
      
      if(number == null) {
         throw new InternalStateException("Number value was null");
      }
      Value value = operator.operate(number);
      Number result = value.getValue();
      
      return create(scope, result);
   }

   private LiteralValue create(Scope scope, Number result) throws Exception {
      Class type = result.getClass();
      
      if(type == Integer.class) {
         return new LiteralValue(result, INTEGER);
      }
      if(type == Double.class) {
         return new LiteralValue(result, DOUBLE);
      }
      if(type == Float.class) {
         return new LiteralValue(result, FLOAT);
      }
      if(type == Byte.class) {
         return new LiteralValue(result, BYTE);
      }
      if(type == Short.class) {
         return new LiteralValue(result, SHORT);
      }
      if(type == Long.class) {
         return new LiteralValue(result, LONG);
      }
      return new LiteralValue(result, NUMBER);
   }
}