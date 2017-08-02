package org.snapscript.tree.literal;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.parse.StringToken;

public class TextLiteral extends Literal {
   
   private final StringToken token;
   
   public TextLiteral(StringToken token) {
      this.token = token;
   }

   @Override
   protected Value create(Scope scope) throws Exception {
      String text = token.getValue();
      
      if(text == null) {
         throw new InternalStateException("Text value was null");
      }
      return ValueType.getTransient(text);
   }
}