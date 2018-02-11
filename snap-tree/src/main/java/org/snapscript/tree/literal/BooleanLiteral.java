package org.snapscript.tree.literal;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.parse.StringToken;

public class BooleanLiteral extends Literal {
   
   private StringToken token;
   
   public BooleanLiteral(StringToken token) {
      this.token = token;
   }
 
   @Override
   protected Value create(Scope scope) throws Exception{
      String text = token.getValue();
      
      if(text == null) {
         throw new InternalStateException("Boolean value is null");
      }
      Module module = scope.getModule();
      Type constraint = module.getType(Boolean.class);
      Boolean value = Boolean.parseBoolean(text);
      
      return Value.getTransient(value, constraint);
   }      
}