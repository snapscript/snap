/*
 * RepeatGrammar.java December 2016
 *
 * Copyright (C) 2016, Niall Gallagher <niallg@users.sf.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */

package org.snapscript.parse;

public class RepeatGrammar implements Grammar {

   private final Grammar grammar;
   private final boolean once;
   
   public RepeatGrammar(Grammar grammar) {
      this(grammar, false);
   }
   
   public RepeatGrammar(Grammar grammar, boolean once) {
      this.grammar = grammar; 
      this.once = once;
   }    
   
   @Override
   public GrammarMatcher create(GrammarCache cache, int length) {
      GrammarMatcher matcher = grammar.create(cache, length);
      return new RepeatMatcher(matcher, once);
   }     

   private static class RepeatMatcher implements GrammarMatcher {

      private final GrammarMatcher matcher;  
      private final boolean once;

      public RepeatMatcher(GrammarMatcher matcher, boolean once) {
         this.matcher = matcher;   
         this.once = once;
      } 
      
      @Override
      public boolean check(SyntaxChecker checker, int depth) {    
         int count = 0;
   
         while(true) {   
            if(!matcher.check(checker, depth)) {            
               break;               
            }      
            count++;
         }
         if(once) {
            return count > 0;
         }
         return true;
      }
   
      @Override
      public boolean build(SyntaxBuilder builder, int depth) {    
         int count = 0;
   
         while(true) {   
            if(!matcher.build(builder, depth)) {            
               break;               
            }      
            count++;
         }
         if(once) {
            return count > 0;
         }
         return true;
      }
      
      @Override
      public String toString() {
         return String.format("%s%s", once ? "+" : "*", matcher);
      }
   }
}

