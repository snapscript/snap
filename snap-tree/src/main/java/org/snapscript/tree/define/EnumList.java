/*
 * EnumList.java December 2016
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

package org.snapscript.tree.define;

import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;

public class EnumList implements TypePart {
   
   private final EnumValue[] values;
   
   public EnumList(EnumValue... values){
      this.values = values;
   }
   
   @Override
   public Initializer define(Initializer initializer, Type type) throws Exception {
      return null;
   }

   @Override
   public Initializer compile(Initializer statement, Type type) throws Exception {
      InitializerCollector collector = new InitializerCollector();
      int index = 0;
      
      for(EnumValue value : values) {
         Initializer initializer = value.compile(type, index++);
         
         if(initializer != null) {
            collector.update(initializer);
         }
      }
      return collector;
   }   
}
