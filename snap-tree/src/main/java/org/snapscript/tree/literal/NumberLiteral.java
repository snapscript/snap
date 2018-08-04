package org.snapscript.tree.literal;

import static org.snapscript.core.variable.Constant.BYTE;
import static org.snapscript.core.variable.Constant.DOUBLE;
import static org.snapscript.core.variable.Constant.FLOAT;
import static org.snapscript.core.variable.Constant.INTEGER;
import static org.snapscript.core.variable.Constant.LONG;
import static org.snapscript.core.variable.Constant.NUMBER;
import static org.snapscript.core.variable.Constant.SHORT;

import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.module.Module;
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
      Value value = operator.operate(scope, number);
      Number result = value.getValue();
      
      return create(scope, result);
   }

   private LiteralValue create(Scope scope, Number result) throws Exception {
      Class type = result.getClass();
      Module module = scope.getModule();
      
      if(type == Integer.class) {
         return new LiteralValue(result, module, INTEGER);
      }
      if(type == Double.class) {
         return new LiteralValue(result, module, DOUBLE);
      }
      if(type == Float.class) {
         return new LiteralValue(result, module, FLOAT);
      }
      if(type == Byte.class) {
         return new LiteralValue(result, module, BYTE);
      }
      if(type == Short.class) {
         return new LiteralValue(result, module, SHORT);
      }
      if(type == Long.class) {
         return new LiteralValue(result, module, LONG);
      }
      return new LiteralValue(result, module, NUMBER);
   }
}