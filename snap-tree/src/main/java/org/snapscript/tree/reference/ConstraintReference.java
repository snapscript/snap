package org.snapscript.tree.reference;

import static org.snapscript.core.constraint.Constraint.TYPE;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.literal.Literal;

public class ConstraintReference extends Literal {

   private final Evaluation evaluation;

   public ConstraintReference(Evaluation evaluation) {
      this.evaluation = evaluation;
   }

   @Override
   protected LiteralValue create(Scope scope) throws Exception {
      Value value = evaluation.evaluate(scope, null);
      Constraint constraint = value.getValue();
      Type type = constraint.getType(scope);
      
      if(type == null) {
         throw new InternalStateException("Could not find constraint");
      }
      return new LiteralValue(type, TYPE);
   }
}
