package org.snapscript.tree.literal;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
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
   protected Value create(Scope scope) throws Exception {
      return ValueType.getTransient(null);
   }
}