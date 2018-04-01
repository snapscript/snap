package org.snapscript.tree.literal;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.scope.Scope;
import org.snapscript.parse.StringToken;

public class NullLiteral extends Literal {
   
   private final StringToken token;

   public NullLiteral() {
      this(null);
   }
   
   public NullLiteral(StringToken token) {
      this.token = token;
   }

   @Override
   protected LiteralValue create(Scope scope) throws Exception {
      return new LiteralValue(null, NONE);
   }
}