package org.snapscript.tree.resume;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Promise;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public class AwaitExpression extends Evaluation {

   private final Evaluation evaluation;

   public AwaitExpression(Evaluation evaluation) {
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
   public Value evaluate(Scope scope, Value left) throws Exception {
      Value value = evaluation.evaluate(scope, left);
      Object object = value.getValue();

      if(Promise.class.isInstance(object)) {
         Promise promise = (Promise)object;
         Object result = promise.get();

         return Value.getTransient(result);
      }
      return value;
   }
}
