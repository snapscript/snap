package org.snapscript.tree.reference;

import static org.snapscript.core.constraint.Constraint.SET;
import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.scope.Scope;
import org.snapscript.parse.StringToken;
import org.snapscript.tree.literal.Literal;

public class SetReference extends Literal {
   
   public SetReference(StringToken token) {
      super();
   }
   
   @Override
   protected LiteralValue create(Scope scope) {
      return new LiteralValue(SET, NONE);
   }
}