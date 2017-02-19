/*
 * LiteralGrammar.java December 2016
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

public class LiteralGrammar implements Grammar {

   private final GrammarMatcher matcher;
   
   public LiteralGrammar(String value) {
      this.matcher = new LiteralMatcher(value);
   }
   
   @Override
   public GrammarMatcher create(GrammarCache cache, int length) {
      return matcher;
   } 
   
   private static class LiteralMatcher implements GrammarMatcher {
      
      private final String value;
      
      public LiteralMatcher(String value) {
         this.value = value;
      }
      
      @Override
      public boolean check(SyntaxChecker checker, int depth) {
         return checker.literal(value);
      }
      
      @Override
      public boolean build(SyntaxBuilder builder, int depth) {
         return builder.literal(value);
      }
      
      @Override
      public String toString() {
         return String.format("'%s'", value);
      }
   }
}
