package org.snapscript.parse;

import static org.snapscript.parse.TokenType.LITERAL;

public class TokenMerger {
   
   public TokenMerger() {
      super();
   }
   
   public Token merge(Token current, Token update){
      if(current != null) {
         String text = combine(current, update);
         
         if(text!= null) {
            Line line = current.getLine();
            short type = current.getType();
            
            return new StringToken(text, line, type);
         }
      }
      return update;
   }
   
   private String combine(Token current, Token update){
      short type = current.getType();
      
      if(type == LITERAL.mask) {
         String prefix = current.toString();
         String suffix = update.toString();
      
         return combine(prefix, suffix);
      }
      return null;
   }
   
   private String combine(String prefix, String suffix){
      int length = prefix.length();
      char last = prefix.charAt(length -1);
      char first = suffix.charAt(0);

      if(!identifier(last) && !identifier(first)) {
         return prefix.concat(suffix);
      }
      return null;
   }
   
   private boolean identifier(char value) {
      if(value >= 'a' && value <='z') {
         return true;
      }
      if(value >= 'A' && value <= 'Z') {
         return true;
      }
      return value >= '0' && value <= '9';
   }
}
