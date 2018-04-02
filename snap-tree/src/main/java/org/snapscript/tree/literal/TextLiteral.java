package org.snapscript.tree.literal;

import static org.snapscript.core.constraint.Constraint.STRING;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.parse.StringToken;

public class TextLiteral extends Literal {
   
   private final StringToken token;
   
   public TextLiteral(StringToken token) {
      this.token = token;
   }

   @Override
   protected LiteralValue create(Scope scope) throws Exception {
      String text = token.getValue();
      
      if(text == null) {
         throw new InternalStateException("Text value was null");
      }
      return new LiteralValue(text, STRING);
   }
}