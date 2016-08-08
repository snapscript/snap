package org.snapscript.tree.literal;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.parse.StringToken;

public class BooleanLiteral extends Literal {
   
   private StringToken token;
   private Boolean value;
   
   public BooleanLiteral(StringToken token) {
      this.token = token;
   }
 
   @Override
   protected Value create(Scope scope) throws Exception{
      if(value == null) {
         String text = token.getValue();
         
         if(text == null) {
            throw new InternalStateException("Boolean value is null");
         }
         value = Boolean.parseBoolean(text);
      }
      return ValueType.getTransient(value);
   }      
}