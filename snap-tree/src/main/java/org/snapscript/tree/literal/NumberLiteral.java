package org.snapscript.tree.literal;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
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
   protected Value create(Scope scope) throws Exception {
      Number number = token.getValue();
      
      if(number == null) {
         throw new InternalStateException("Number value was null");
      }
      Value value = operator.operate(number);
      Object result = value.getValue();
      Class real = result.getClass();
      Module module = scope.getModule();
      Type constraint = module.getType(real);
      
      return Value.getTransient(result, constraint);
   }
}