/*
 * SymbolGrammar.java December 2016
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

public class SymbolGrammar implements Grammar {

   private final GrammarMatcher matcher;
   
   public SymbolGrammar(Symbol symbol, String value, int index) {
      this.matcher = new SymbolMatcher(symbol, value, index);
   }

   @Override
   public GrammarMatcher create(GrammarCache cache, int length) {
      return matcher;
   }  
   
   private static class SymbolMatcher implements GrammarMatcher {
      
      private final TokenType type;
      private final Symbol symbol;
      private final String value;
      private final int index;
      
      public SymbolMatcher(Symbol symbol, String value, int index) {
         this.type = symbol.type;
         this.symbol = symbol;
         this.value = value; 
         this.index = index;
      }
      
      @Override
      public boolean check(SyntaxChecker checker, int depth) {
         int mask = checker.peek();
         
         if((mask & type.mask) != 0) {
            int mark = checker.mark(index);
      
            if(symbol.read(checker)) {
               checker.commit(mark, index);
               return true;
            }
         }
         return false;
      }
   
      @Override
      public boolean build(SyntaxBuilder builder, int depth) {
         int mask = builder.peek();
         
         if((mask & type.mask) != 0) {
            SyntaxBuilder child = builder.mark(index);
      
            if(symbol.read(child)) {
               child.commit();
               return true;
            }
         }
         return false;
      }
      
      @Override
      public String toString() {
         return String.format("[%s]", value);
      } 
   }
}
