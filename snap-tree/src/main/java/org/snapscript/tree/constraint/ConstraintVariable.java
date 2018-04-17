package org.snapscript.tree.constraint;

import static org.snapscript.core.constraint.Constraint.TYPE;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.tree.literal.Literal;

public class ConstraintVariable extends Literal {

   private final Constraint constraint;

   public ConstraintVariable(Constraint constraint) {
      this.constraint = constraint;
   }

   @Override
   protected LiteralValue create(Scope scope) throws Exception {
      Type type = constraint.getType(scope);
      
      if(type == null) {
         throw new InternalStateException("Could not find constraint");
      }
      return new LiteralValue(type, TYPE);
   }
}