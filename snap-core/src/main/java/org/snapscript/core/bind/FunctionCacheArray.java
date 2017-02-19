/*
 * FunctionCacheArray.java December 2016
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

package org.snapscript.core.bind;

import java.util.concurrent.atomic.AtomicReferenceArray;

public class FunctionCacheArray {
   
   private final AtomicReferenceArray<FunctionCache> array;
   private final int capacity;
   private final int expand;
   
   public FunctionCacheArray(int capacity, int expand) {
      this.array = new AtomicReferenceArray<FunctionCache>(capacity);
      this.capacity = capacity;
      this.expand = expand;
   }
   
   public FunctionCacheArray copy(int require) {
      int length = array.length();
      
      if(require >= length) {
         FunctionCacheArray copy = new FunctionCacheArray(require + expand, expand);
         
         for(int i = 0; i < length; i++) {
            FunctionCache cache = array.get(i);
            copy.set(i, cache);
         }
         return copy;
      }
      return this;
   }
   
   public FunctionCache get(int index) {
      return array.get(index);
   }
   
   public void set(int index, FunctionCache cache) {
      array.set(index, cache);
   }

   public int length(){
      return capacity;
   }
}
