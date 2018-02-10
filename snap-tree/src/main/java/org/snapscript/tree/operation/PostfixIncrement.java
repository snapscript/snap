package org.snapscript.tree.operation;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.parse.Token;
import org.snapscript.tree.math.NumericConverter;

public class PostfixIncrement extends Evaluation {
   
   private final Evaluation evaluation;
   private final Token operator;
   
   public PostfixIncrement(Evaluation evaluation, Token operator) {
      this.evaluation = evaluation;
      this.operator = operator;
   }
   
   @Override
   public void compile(Scope scope) throws Exception {
      evaluation.compile(scope);
   }
   
   @Override
   public Value validate(Scope scope, Object left) throws Exception {
      Value reference = evaluation.validate(scope, left);
      Object number = reference.getValue();

      if(number != null && number.getClass() != Object.class) {
         if(Number.class.isInstance(number)) {
            throw new IllegalStateException("Not a number");
         }
      }
      return Value.getTransient(number);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception { // this is rubbish
      Value reference = evaluation.evaluate(scope, left);
      Number number = reference.getNumber();
      NumericConverter converter = NumericConverter.resolveConverter(number);
      Value value = converter.increment(number);
      Number result = value.getNumber();
      
      reference.setValue(result);
      
      return Value.getTransient(number);
   }
}