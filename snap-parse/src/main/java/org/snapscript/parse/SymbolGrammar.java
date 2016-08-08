package org.snapscript.parse;

public class SymbolGrammar implements Grammar {

   private final GrammarMatcher matcher;
   
   public SymbolGrammar(Symbol symbol, String value, int index) {
      this.matcher = new SymbolMatcher(symbol, value, index);
   }

   @Override
   public GrammarMatcher create(GrammarCache cache) {
      return matcher;
   }  
   
   private static class SymbolMatcher implements GrammarMatcher {
      
      private final Symbol symbol;
      private final String value;
      private final int index;
      
      public SymbolMatcher(Symbol symbol, String value, int index) {
         this.symbol = symbol;
         this.value = value; 
         this.index = index;
      }
   
      @Override
      public boolean match(SyntaxBuilder builder, int depth) {
         SyntaxBuilder child = builder.mark(index);
   
         if(symbol.read(child)) {
            child.commit();
            return true;
         }
         return false;
      }
      
      @Override
      public String toString() {
         return String.format("[%s]", value);
      } 
   }
}
