/*
 * GrammarIndexer.java December 2016
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

public class GrammarIndexer {
   
   private final List<String> literals;
   private final List<String> values;

   public GrammarIndexer() {
      this.literals = new ArrayList<String>();
      this.values = new ArrayList<String>();
   }
   
   public List<String> list(){
      return literals;
   }
   
   public String literal(String value) {
      int index = literals.indexOf(value);
      
      if (index == -1) {
         literals.add(value);
         return value;
      }
      return literals.get(index);
   }

   public String resolve(int index) {
      return literals.get(index);
   }

   public int index(String value) {
      int index = values.indexOf(value);
      int size = values.size();
      
      if (index == -1) {
         values.add(value);
         return size;
      }
      return index;
   }

   public String value(int index) {
      return values.get(index);
   }
}