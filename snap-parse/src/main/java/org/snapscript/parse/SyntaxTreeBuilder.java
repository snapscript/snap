/*
 * SyntaxTreeBuilder.java December 2016
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

public class SyntaxTreeBuilder {

   private final SourceProcessor processor;
   private final GrammarIndexer indexer;
   
   public SyntaxTreeBuilder(GrammarIndexer indexer) {
      this.processor = new SourceProcessor(100);
      this.indexer = indexer;
   }

   public SyntaxTree create(String resource, String text, String grammar) {
      char[] array = text.toCharArray();
      
      if(array.length == 0) {
         throw new ParseException("Source text is empty for '" + resource + "'");
      }
      SourceCode source = processor.process(text);
      char[] original = source.getOriginal();
      char[] compress = source.getSource();
      short[] lines = source.getLines();
      short[]types = source.getTypes();

      return new SyntaxTree(indexer, resource, grammar, original, compress, lines, types);
   }       
}

