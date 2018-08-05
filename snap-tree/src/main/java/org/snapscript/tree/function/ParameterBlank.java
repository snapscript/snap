package org.snapscript.tree.function;

import static org.snapscript.core.variable.Constant.STRING;

import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.parse.StringToken;
import org.snapscript.tree.literal.Literal;

public class ParameterBlank extends Literal {
   
   private final StringToken token;
   
   public ParameterBlank(StringToken token) {
      this.token = token;
   }

   @Override
   protected LiteralValue create(Scope scope) throws Exception {
      String text = token.getValue();
      int hash = System.identityHashCode(this);
      
      if(text == null) {
         throw new InternalStateException("Text value was null");
      }
      return new LiteralValue(text + hash, STRING);
   }
}