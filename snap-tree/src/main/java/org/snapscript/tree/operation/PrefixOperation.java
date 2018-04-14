package org.snapscript.tree.operation;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.parse.StringToken;

public class PrefixOperation extends Evaluation {
   
   private final PrefixOperator operator;
   private final Evaluation evaluation;
   
   public PrefixOperation(StringToken operator, Evaluation evaluation) {
      this.operator = PrefixOperator.resolveOperator(operator);
      this.evaluation = evaluation;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      evaluation.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      return evaluation.compile(scope, left);
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Value reference = evaluation.evaluate(scope, null);
      return operator.operate(reference);
   } 
}