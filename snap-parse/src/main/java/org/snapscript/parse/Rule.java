/*
 * Rule.java December 2016
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

public class Rule {
   
   private final RuleType type;
   private final String origin;
   private final String symbol;
   
   public Rule(RuleType type, String symbol, String origin) {
      this.origin = origin;
      this.symbol = symbol;
      this.type = type;
   }           
   
   public String getOrigin(){
      return origin;
   }
   
   public String getSymbol(){
      return symbol;
   }

   public RuleType getType() {
      return type;
   }     
   
   @Override
   public String toString(){
      return symbol;
   }
}
