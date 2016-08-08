package org.snapscript.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MatchFirstGrammar implements Grammar {
   
   private final List<Grammar> grammars;
   private final String name;
   
   public MatchFirstGrammar(List<Grammar> grammars, String name) {
      this.grammars = grammars;
      this.name = name;
   }       

   @Override
   public GrammarMatcher create(GrammarCache cache) {
      List<GrammarMatcher> matchers = new ArrayList<GrammarMatcher>();
      
      for(Grammar grammar : grammars) {
         GrammarMatcher matcher = grammar.create(cache);
         matchers.add(matcher);
      }
      return new MatchFirstMatcher(matchers, name);
   } 
   
   private static class MatchFirstMatcher implements GrammarMatcher {
      
      private final Map<Integer, GrammarMatcher> cache;
      private final List<GrammarMatcher> matchers;
      private final Set<Integer> failure;
      private final String name;
      
      public MatchFirstMatcher(List<GrammarMatcher> matchers, String name) {
         this.cache = new HashMap<Integer, GrammarMatcher>();
         this.failure = new HashSet<Integer>();
         this.matchers = matchers;
         this.name = name;
      }    
   
      @Override
      public boolean match(SyntaxBuilder builder, int depth) {
         Integer position = builder.position();
         
         if(!failure.contains(position)) {
            GrammarMatcher best = cache.get(position);
            
            if(best == null) {
               int count = matchers.size();    
                  
               for(int i = 0; i < count; i++) {
                  GrammarMatcher matcher = matchers.get(i);   
         
                  if(matcher.match(builder, depth + 1)) {
                     cache.put(position, matcher);
                     return true;
                  }               
               }                  
               failure.add(position);            
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
