package org.snapscript.parse;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.common.BitSet;

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
   public GrammarMatcher create(GrammarCache cache, int length) {
      List<GrammarMatcher> matchers = new ArrayList<GrammarMatcher>();
      
      for(Grammar grammar : grammars) {
         GrammarMatcher matcher = grammar.create(cache, length);
         matchers.add(matcher);
      }
      return new MatchAllMatcher(matchers, name, index, length);
   } 
   
   public static class MatchAllMatcher implements GrammarMatcher {
      
      private final List<GrammarMatcher> matchers;
      private final BitSet success;
      private final BitSet failure;
      private final String name;
      private final int index;

      public MatchAllMatcher(List<GrammarMatcher> matchers, String name, int index, int length) {
         this.success = new BitSet(length);
         this.failure = new BitSet(length);
         this.matchers = matchers;
         this.index = index;
         this.name = name;
      }
      
      @Override
      public boolean check(SyntaxChecker checker, int depth) {
         int position = checker.position();
         
         if(depth == 0) {
            for(GrammarMatcher matcher : matchers) {               
               if(!matcher.check(checker, depth + 1)) {
                  return false; 
               }
            }
            return true;
         }
         if(!failure.get(position)) {
            if(!success.get(position)) {
               int mark = checker.mark(index);   
               int require = matchers.size();
               int count = 0;
               
               if(mark != -1) {            
                  for(GrammarMatcher grammar : matchers) {               
                     if(!grammar.check(checker, 0)) {
                        failure.set(position);
                        return false;
                     }
                     count++;                     
                  }
                  checker.reset(mark, index);
               }           
               if(count == require) {
                  success.set(position);
               }
            }
            if(success.get(position)) {
               for(GrammarMatcher grammar : matchers) {               
                  if(!grammar.check(checker, 0)) {
                     throw new ParseException("Could not read node in " + name);  
                  }
               }
               return true;
            } 
         }
         return false;
      }
   
      @Override
      public boolean build(SyntaxBuilder builder, int depth) {
         int position = builder.position();
         
         if(depth == 0) {
            for(GrammarMatcher matcher : matchers) {               
               if(!matcher.build(builder, depth + 1)) {
                  return false; 
               }
            }
            return true;
         }
         if(!failure.get(position)) {
            if(!success.get(position)) {
               SyntaxBuilder child = builder.mark(index);   
               int require = matchers.size();
               int count = 0;
               
               if(child != null) {            
                  for(GrammarMatcher grammar : matchers) {               
                     if(!grammar.build(child, 0)) {
                        failure.set(position);
                        break;
                     }
                     count++;
                  }
                  child.reset();
               }           
               if(count == require) {
                  success.set(position);
               }
            }
            if(success.get(position)) {
               for(GrammarMatcher grammar : matchers) {               
                  if(!grammar.build(builder, 0)) {
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