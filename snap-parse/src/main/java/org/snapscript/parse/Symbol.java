/*
 * Symbol.java December 2016
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

public enum Symbol {
   IDENTIFIER(TokenType.IDENTIFIER, "identifier") {
      @Override
      public boolean read(TokenReader reader) {
         return reader.identifier();
      }
   },
   TYPE(TokenType.TYPE, "type") {
      @Override
      public boolean read(TokenReader reader) {
         return reader.type();
      }
   },   
   QUALIFIER(TokenType.QUALIFIER, "qualifier") {
      @Override
      public boolean read(TokenReader reader) {
         return reader.qualifier();
      }
   },   
   HEXIDECIMAL(TokenType.HEXIDECIMAL, "hexidecimal") {
      @Override
      public boolean read(TokenReader reader) {
         return reader.hexidecimal();
      }
   },   
   BINARY(TokenType.BINARY, "binary") {
      @Override
      public boolean read(TokenReader reader) {
         return reader.binary();
      }
   },
   DECIMAL(TokenType.DECIMAL, "decimal") {
      @Override
      public boolean read(TokenReader reader) {
         return reader.decimal();
      }
   },
   TEXT(TokenType.TEXT, "text") {
      @Override
      public boolean read(TokenReader reader) {
         return reader.text();
      }
   },
   TEMPLATE(TokenType.TEMPLATE, "template") {
      @Override
      public boolean read(TokenReader reader) {
         return reader.template();
      }
   };
   
   public final TokenType type;
   public final String name;
   
   private Symbol(TokenType type, String name) {
      this.type = type;
      this.name = name;
   }

   public abstract boolean read(TokenReader builder);
}
