/*
 * ArrayValue.java December 2016
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

package org.snapscript.tree.collection;

import org.snapscript.core.Value;
import java.lang.reflect.Array;

public class ArrayValue extends Value {
   
   private final Object array;
   private final Integer index;
   private final Class type;
   
   public ArrayValue(Object array, Integer index) {
      this.type = array.getClass();
      this.array = array;
      this.index = index;
   }
   
   @Override
   public Class getType() {
      return type.getComponentType();
   }
   
   @Override
   public Object getValue(){
      return Array.get(array, index);
   }
   
   @Override
   public void setValue(Object value){
      Array.set(array, index, value);
   }       
   
   @Override
   public String toString() {
      return String.valueOf(array);
   }
}