package org.snapscript.parse;

public class LiteralGrammar implements Grammar {

   private final GrammarMatcher matcher;
   
   public LiteralGrammar(String value) {
      this.matcher = new LiteralMatcher(value);
   }
   
   @Override
   public GrammarMatcher create(GrammarCache cache) {
      return matcher;
   } 
   
   private static class LiteralMatcher implements GrammarMatcher {
      
      private final String value;
      
      public LiteralMatcher(String value) {
         this.value = value;
      }
      
      @Override
      public boolean match(SyntaxBuilder builder, int depth) {
         return builder.literal(value);
      }
      
      @Override
      public String toString() {
         return String.format("'%s'", value);
      }
   }
}
