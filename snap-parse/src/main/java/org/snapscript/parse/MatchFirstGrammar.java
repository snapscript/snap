package org.snapscript.parse;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.common.BitSet;
import org.snapscript.common.SparseArray;

public class MatchFirstGrammar implements Grammar {
   
   private final List<Grammar> grammars;
   private final String name;
   
   public MatchFirstGrammar(List<Grammar> grammars, String name) {
      this.grammars = grammars;
      this.name = name;
   }       

   @Override
   public GrammarMatcher create(GrammarCache cache, int length) {
      List<GrammarMatcher> matchers = new ArrayList<GrammarMatcher>();
      
      for(Grammar grammar : grammars) {
         GrammarMatcher matcher = grammar.create(cache, length);
         matchers.add(matcher);
      }
      return new MatchFirstMatcher(matchers, name, length);
   } 
   
   private static class MatchFirstMatcher implements GrammarMatcher {
      
      private final SparseArray<GrammarMatcher> cache;
      private final List<GrammarMatcher> matchers;
      private final BitSet failure;
      private final String name;
      
      public MatchFirstMatcher(List<GrammarMatcher> matchers, String name, int length) {
         this.cache = new SparseArray<GrammarMatcher>(length);
         this.failure = new BitSet(length);
         this.matchers = matchers;
         this.name = name;
      }    
   
      @Override
      public boolean match(SyntaxBuilder builder, int depth) {
         int position = builder.position();
         
         if(!failure.get(position)) {
            GrammarMatcher best = cache.get(position);
            
            if(best == null) {
               int count = matchers.size();    
                  
               for(int i = 0; i < count; i++) {
                  GrammarMatcher matcher = matchers.get(i);   
         
                  if(matcher.match(builder, depth + 1)) {
                     cache.set(position, matcher);
                     return true;
                  }               
               }                  
               failure.set(position);            
            }
            if(best != null) {            
               if(!best.match(builder, 0)) {
                  throw new ParseException("Could not read node in " + name);  
               }     
               return true;
            }      
         }
         return false;
      }
      
      @Override
      public String toString() {
         return String.format("{%s}", matchers);
      }    
   }
}
