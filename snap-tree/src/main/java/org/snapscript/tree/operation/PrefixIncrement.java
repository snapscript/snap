package org.snapscript.tree.operation;

import org.snapscript.core.Bug;
import org.snapscript.core.Constraint;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.parse.Token;
import org.snapscript.tree.math.NumericConverter;

public class PrefixIncrement extends Evaluation {
   
   private final Evaluation evaluation;
   private final Token operator;
   
   public PrefixIncrement(Token operator, Evaluation evaluation) {
      this.evaluation = evaluation;
      this.operator = operator;
   }
   
   @Override
   public void compile(Scope scope) throws Exception {
      evaluation.compile(scope);
   }
   
   @Override
   public Constraint validate(Scope scope, Constraint left) throws Exception {
      return evaluation.validate(scope, left);
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