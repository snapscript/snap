package org.snapscript.tree.literal;

import static org.snapscript.core.constraint.Constraint.BOOLEAN;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.parse.StringToken;

public class BooleanLiteral extends Literal {
   
   private StringToken token;
   
   public BooleanLiteral(StringToken token) {
      this.token = token;
   }
 
   @Override
   protected LiteralValue create(Scope scope) throws Exception{
      String text = token.getValue();
      
      if(text == null) {
         throw new InternalStateException("Boolean value is null");
      }
      Boolean value = Boolean.parseBoolean(text);
      return new LiteralValue(value, BOOLEAN);
   }      
}