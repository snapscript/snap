package org.snapscript.tree.operation;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.parse.Token;

public class PrefixDecrement implements Evaluation {
   
   private final Evaluation evaluation;
   private final Token operator;
   
   public PrefixDecrement(Token operator, Evaluation evaluation) {
      this.evaluation = evaluation;
      this.operator = operator;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception { // this is rubbish
      Value reference = evaluation.evaluate(scope, left);
      Number number = reference.getNumber();
      NumericConverter converter = NumericConverter.resolveConverter(number);
      Value value = converter.decrement(number);
      Number result = value.getNumber();
      
      reference.setValue(result);

      return reference;
   }
}