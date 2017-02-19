/*
 * TokenConsumer.java December 2016
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

public abstract class TokenConsumer implements TokenReader {

   protected LexicalAnalyzer analyzer;
   protected Token value;

   protected TokenConsumer() {
      this(null);
   }      
   
   protected TokenConsumer(LexicalAnalyzer analyzer) {
      this.analyzer = analyzer;
   }

   @Override
   public boolean literal(String text) {
      Token token = analyzer.literal(text);

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }

   @Override
   public boolean decimal() {
      Token token = analyzer.decimal();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }
   
   @Override
   public boolean binary() {
      Token token = analyzer.binary();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }

   @Override
   public boolean hexidecimal() {
      Token token = analyzer.hexidecimal();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }

   @Override
   public boolean identifier() {
      Token token = analyzer.identifier();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }
   
   @Override
   public boolean qualifier() {
      Token token = analyzer.qualifier();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }     
   
   @Override
   public boolean type() {
      Token token = analyzer.type();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }       

   @Override
   public boolean text() {
      Token token = analyzer.text();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }
   
   @Override
   public boolean template() {
      Token token = analyzer.template();

      if (token != null) {
         value = token;
         return true;
      }
      return false;
   }
}
