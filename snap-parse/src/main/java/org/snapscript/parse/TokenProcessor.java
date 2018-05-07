package org.snapscript.parse;

import static org.snapscript.parse.TokenType.LITERAL;

public class TokenProcessor {
   
   public TokenProcessor() {
      super();
   }
   
   public Token process(Token current, Token update){
      if(current != null) {
         short type = current.getType();
         
         if(type == LITERAL.mask) {
            String prefix = current.toString();
            String suffix = update.toString();
            int length = prefix.length();
            char last = prefix.charAt(length -1);
            char first = suffix.charAt(0);

            if(!Character.isLetter(last)&& !Character.isLetter(first)) {
               String text = prefix.concat(suffix);
               Line line = current.getLine();
               
               return new StringToken(text, line, type);
            }
         }
      }
      return update;
   }
}
