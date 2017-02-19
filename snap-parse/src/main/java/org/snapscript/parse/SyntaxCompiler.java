/*
 * SyntaxCompiler.java December 2016
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

import java.util.LinkedHashMap;
import java.util.Map;

public class SyntaxCompiler {
   
   private final Map<String, Grammar> grammars;
   private final GrammarCompiler compiler;
   private final GrammarResolver resolver;
   private final GrammarIndexer indexer;
   private final SyntaxParser parser;

   public SyntaxCompiler() {
      this.grammars = new LinkedHashMap<String, Grammar>();      
      this.resolver = new GrammarResolver(grammars);
      this.indexer = new GrammarIndexer();
      this.parser = new SyntaxParser(resolver, indexer);      
      this.compiler = new GrammarCompiler(resolver, indexer);      
   } 

   public synchronized SyntaxParser compile() {
      if(grammars.isEmpty()) { 
         Syntax[] language = Syntax.values();
         
         for(Syntax syntax : language) {
            String name = syntax.getName();
            String value = syntax.getGrammar();
            Grammar grammar = compiler.process(name, value);
            
            grammars.put(name, grammar);
         }
      }
      return parser;
   }
}
