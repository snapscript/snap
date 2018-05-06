package org.snapscript.tree.reference;

import static org.snapscript.core.constraint.Constraint.MAP;
import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.scope.Scope;
import org.snapscript.parse.StringToken;
import org.snapscript.tree.literal.Literal;

public class MapReference extends Literal {
   
   public MapReference(StringToken token) {
      super();
   }
   
   @Override
   protected LiteralValue create(Scope scope) {
      return new LiteralValue(MAP, NONE);
   }
}