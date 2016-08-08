package org.snapscript.parse;

public class OptionalGrammar implements Grammar {

   private final Grammar grammar; 
   
   public OptionalGrammar(Grammar grammar) {
      this.grammar = grammar;
   }
   
   @Override
   public GrammarMatcher create(GrammarCache cache) {
      GrammarMatcher matcher = grammar.create(cache);
      return new OptionalMatcher(matcher);
   } 
   
   private static class OptionalMatcher implements GrammarMatcher {
      
      private final GrammarMatcher matcher;
      
      public OptionalMatcher(GrammarMatcher matcher) {
         this.matcher = matcher; 
      }
   
      @Override
      public boolean match(SyntaxBuilder builder, int depth) {      
         matcher.match(builder, depth);
         return true;
      }
      
      @Override
      public String toString() {
         return String.format("?%s", matcher);
      }    
   }
}

