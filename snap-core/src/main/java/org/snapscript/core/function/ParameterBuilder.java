/*
 * ParameterBuilder.java December 2016
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

package org.snapscript.core.function;

import org.snapscript.core.Type;

public class ParameterBuilder {
   
   private static final String[] PREFIX = {
   "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", 
   "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
   
   public ParameterBuilder() {
      super();
   }
   
   public Parameter create(Type type, String name) {
      return new Parameter(name, type);
   }

   public Parameter create(Type type, int index) {
      return create(type, index, false);
   }
   
   public Parameter create(Type type, int index, boolean variable) {
      String prefix = PREFIX[index % PREFIX.length];
      
      if(index > PREFIX.length) {
         prefix += index / PREFIX.length;
      }
      return new Parameter(prefix, type, variable);
   }
}
