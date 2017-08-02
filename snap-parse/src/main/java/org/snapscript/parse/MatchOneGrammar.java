package org.snapscript.parse;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.common.BitSet;
import org.snapscript.common.SparseArray;

public class MatchOneGrammar implements Grammar {
   
   private final List<Grammar> grammars;
   private final String name;
   private final int index;
   
   public MatchOneGrammar(List<Grammar> grammars, String name, int index) {
      this.grammars = grammars;
      this.index = index;
      this.name = name;
   }       
   
   @Override
   public GrammarMatcher create(GrammarCache cache, int length) {
      List<GrammarMatcher> matchers = new ArrayList<GrammarMatcher>();
      
      for(Grammar grammar : grammars) {
         GrammarMatcher matcher = grammar.create(cache, length);
         matchers.add(matcher);
      }
      return new MatchOneMatcher(matchers, name, index, length);
   }
   
   private static class MatchOneMatcher implements GrammarMatcher {
      
      private final SparseArray<GrammarMatcher> cache;
      private final List<GrammarMatcher> matchers;
      private final BitSet failure;
      private final String name;
      private final int index;

      public MatchOneMatcher(List<GrammarMatcher> matchers, String name, int index, int length) {
         this.cache = new SparseArray<GrammarMatcher>(length);
         this.failure = new BitSet(length);
         this.matchers = matchers;
         this.index = index;
         this.name = name;
      }  
      
      @Override
      public boolean check(SyntaxChecker checker, int depth) {
         int position = checker.position();
         
         if(!failure.get(position)) {
            GrammarMatcher best = cache.get(position);
            
            if(best == null) {
               int size = -1;     
                  
               for(GrammarMatcher matcher : matchers) {
                  int mark = checker.mark(index);   
         
                  if(mark != -1) {
                     if(matcher.check(checker, 0)) {
                        int offset = checker.reset(mark, index);
                        
                        if(offset > size) {
                           size = offset;
                           best = matcher;
                        }
                     }
                     checker.reset(mark, index);
                  }
               }                  
               if(best != null) {
                  cache.set(position, best);
               } else {
                  failure.set(position);
               }
            }   
            if(best != null) {            
               if(!best.check(checker, 0)) {
                  throw new ParseException("Could not read node in " + name);  
               }     
               return true;
            } 
         }
         return false;
      }
   
      @Override
      public boolean build(SyntaxBuilder builder, int depth) {
         int position = builder.position();
         
         if(!failure.get(position)) {
            GrammarMatcher best = cache.get(position);
            
            if(best == null) {
               int size = -1;     
                  
               for(GrammarMatcher matcher : matchers) {
                  SyntaxBuilder child = builder.mark(index);   
         
                  if(child != null) {
                     if(matcher.build(child, 0)) {
                        int offset = child.reset();
                        
                        if(offset > size) {
                           size = offset;
                           best = matcher;
                        }
                     } else {
                        child.reset();
                     }
                  }
               }                  
               if(best != null) {
                  cache.set(position, best);
               } else {
                  failure.set(position);
               }
            }
            if(best != null) {            
               if(!best.build(builder, 0)) {
                  throw new ParseException("Could not read node in " + name);  
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