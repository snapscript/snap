/*
 * RuleType.java December 2016
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

public enum RuleType {
   SPLITTER("|"),
   REPEAT("*"),
   REPEAT_ONCE("+"),
   OPTIONAL("?"), 
   OPEN_GROUP("("),
   CLOSE_GROUP(")"),
   OPEN_CHOICE("{"),
   CLOSE_CHOICE("}"),   
   SPECIAL("[", "]"),   
   REFERENCE("<", ">"),
   LITERAL("'", "'");
   
   public final String terminal;
   public final String start;

   private RuleType(String start) {
      this(start, null);
   }
   
   private RuleType(String start, String terminal) {
      this.terminal = terminal;
      this.start = start;
   }
   
   public boolean isOptional() {
      return this == OPTIONAL;
   }
   
   public boolean isSplitter() {
      return this == SPLITTER;
   }
   
   public boolean isRepeat() {
      return this == REPEAT;
   }
   
   public boolean isRepeatOnce() {
      return this == REPEAT_ONCE;
   }
   
   public boolean isOpenChoice(){
      return this == OPEN_CHOICE;
   }    
   
   public boolean isCloseChoice(){
      return this == CLOSE_CHOICE;
   }   
   
   public boolean isOpenGroup(){
      return this == OPEN_GROUP;
   }

   public boolean isCloseGroup(){
      return this == CLOSE_GROUP;
   }
   
   public boolean isSpecial() {
      return this == SPECIAL;
   }    
   
   public boolean isReference() {
      return this == REFERENCE;
   } 
   
   public boolean isLiteral() {
      return this == LITERAL;
   }
}
