package org.snapscript.parse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MatchAllGrammar implements Grammar {

   private final List<Grammar> grammars;
   private final String name;
   private final int index;
   
   public MatchAllGrammar(List<Grammar> grammars, String name, int index) {
      this.grammars = grammars;
      this.index = index;
      this.name = name;
   }  

   @Override
   public GrammarMatcher create(GrammarCache cache) {
      List<GrammarMatcher> matchers = new ArrayList<GrammarMatcher>();
      
      for(Grammar grammar : grammars) {
         GrammarMatcher matcher = grammar.create(cache);
         matchers.add(matcher);
      }
      return new MatchAllMatcher(matchers, name, index);
   } 
   
   public static class MatchAllMatcher implements GrammarMatcher {
      
      private final List<GrammarMatcher> matchers;
      private final Set<Integer> success;
      private final Set<Integer> failure;
      private final String name;
      private final int index;

      public MatchAllMatcher(List<GrammarMatcher> matchers, String name, int index) {
         this.success = new HashSet<Integer>();
         this.failure = new HashSet<Integer>();
         this.matchers = matchers;
         this.index = index;
         this.name = name;
      }
   
      @Override
      public boolean match(SyntaxBuilder builder, int depth) {
         Integer position = builder.position();
         
         if(depth == 0) {
            for(GrammarMatcher matcher : matchers) {               
               if(!matcher.match(builder, depth + 1)) {
                  return false; 
               }
            }
            return true;
         }
         if(!failure.contains(position)) {
            if(!success.contains(position)) {
               SyntaxBuilder child = builder.mark(index);   
               int require = matchers.size();
               int count = 0;
               
               if(child != null) {            
                  for(GrammarMatcher grammar : matchers) {               
                     if(!grammar.match(child, 0)) {
                        failure.add(position);
                        break;
                     }
                     count++;
                  }
                  child.reset();
               }           
               if(count == require) {
                  success.add(position);
               }
            }
            if(success.contains(position)) {
               for(GrammarMatcher grammar : matchers) {               
                  if(!grammar.match(builder, 0)) {
                     throw new ParseException("Could not read node in " + name);  
                  }
               }
               return true;
            } 
         }
         return false;
      }
      
      @Override
      public String toString() {
         return String.valueOf(matchers);
      }
   }
}
