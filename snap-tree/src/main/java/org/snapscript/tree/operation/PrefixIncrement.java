package org.snapscript.tree.operation;

import org.snapscript.core.Evaluation;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.parse.Token;
import org.snapscript.tree.math.NumericConverter;

public class PrefixIncrement extends NumericOperation {
 
   public PrefixIncrement(Token operator, Evaluation evaluation) {
      super(evaluation, operator);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception { // this is rubbish
      Value reference = evaluation.evaluate(scope, left);
      Number number = reference.getNumber();
      NumericConverter converter = NumericConverter.resolveConverter(number);
      Value value = converter.increment(number);
      Number result = value.getNumber();
      
      reference.setValue(result);
      
      return reference;
   }
}