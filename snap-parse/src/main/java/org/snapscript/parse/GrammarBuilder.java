/*
 * GrammarBuilder.java December 2016
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

import java.util.ArrayList;
import java.util.List;

public class GrammarBuilder {

   private final GrammarResolver resolver;
   private final GrammarIndexer indexer;

   public GrammarBuilder(GrammarResolver resolver, GrammarIndexer indexer) {
      this.resolver = resolver;
      this.indexer = indexer;      
   }
   
   public Grammar createLiteral(String text, String origin) { 
      String value = indexer.literal(text);
      return new LiteralGrammar(value);
   }
   
   public Grammar createReference(String text, String origin) {
      int index = indexer.index(text);
      return new ReferenceGrammar(resolver, text, index);
   }   
   
   public Grammar createOptional(Grammar node, String origin) {
      return new OptionalGrammar(node);
   }          
   
   public Grammar createRepeat(Grammar node, String origin) {
      return new RepeatGrammar(node);      
   }
   
   public Grammar createRepeatOnce(Grammar node, String origin) {
      return new RepeatGrammar(node, true);
   }   
   
   public Grammar createSpecial(String text, String origin) {
      Symbol[] symbols = Symbol.values();
      
      for(Symbol symbol : symbols) {
         if(symbol.name.equals(text)) {     
            int index = indexer.index(text);
            return new SymbolGrammar(symbol, text, index); 
         }       
      }
      return null;
   }    
   
   public Grammar createMatchBest(List<Grammar> nodes, String origin) {
      Grammar top = nodes.get(0);      
      int count = nodes.size();
      int index = indexer.index(origin);
      
      if(count > 1) {
         List<Grammar> copy = new ArrayList<Grammar>(nodes);         
         MatchOneGrammar choice = new MatchOneGrammar(copy, origin, index);  
         
         return choice;
      }
      return top;      
   } 
   
   public Grammar createMatchFirst(List<Grammar> nodes, String origin) {
      Grammar top = nodes.get(0);      
      int count = nodes.size();
      int index = indexer.index(origin);
      
      if(count > 1) {
         List<Grammar> copy = new ArrayList<Grammar>(nodes);         
         MatchFirstGrammar choice = new MatchFirstGrammar(copy, origin, index);  
         
         return choice;
      }
      return top;      
   }   
   
   public Grammar createMatchAll(List<Grammar> nodes, String origin) {
      Grammar top = nodes.get(0);      
      int count = nodes.size();
      int index = indexer.index(origin);
      
      if(count > 1) {
         List<Grammar> copy = new ArrayList<Grammar>(nodes);
         MatchAllGrammar group = new MatchAllGrammar(copy, origin, index);
         
         return group;
      }
      return top;      
   }
}
