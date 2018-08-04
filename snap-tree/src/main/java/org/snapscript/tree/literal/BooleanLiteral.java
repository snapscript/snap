package org.snapscript.tree.literal;

import static org.snapscript.core.variable.Constant.BOOLEAN;

import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.module.Module;
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
      Module module = scope.getModule();
      
      return new LiteralValue(value, module, BOOLEAN);
   }      
}