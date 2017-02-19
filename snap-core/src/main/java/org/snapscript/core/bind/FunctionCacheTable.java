/*
 * FunctionCacheTable.java December 2016
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

public class FunctionCacheTable<T> {

   private volatile FunctionCacheIndexer<T> indexer;
   private volatile FunctionCacheArray array;
   private volatile CacheAllocator allocator;
   
   public FunctionCacheTable(FunctionCacheIndexer<T> indexer) {
      this(indexer, 5000);
   }
   
   public FunctionCacheTable(FunctionCacheIndexer<T> indexer, int capacity) {
      this.array = new FunctionCacheArray(capacity, capacity);
      this.allocator = new CacheAllocator(indexer);
      this.indexer = indexer;
   }
   
   public FunctionCache get(T value) {
      int index = indexer.index(value); // index 0 will cluster anonymous types
      int length = array.length();
      
      if(index >= length) {
         return allocator.allocate(value);
      }
      FunctionCache cache = array.get(index);
      
      if(cache == null) {
         return allocator.allocate(value);
      }
      return cache;
   }
   
   private class CacheAllocator {
      
      private final FunctionCacheIndexer<T> indexer;
      
      public CacheAllocator(FunctionCacheIndexer<T> indexer) {
         this.indexer = indexer;
      }
      
      public synchronized FunctionCache allocate(T value) {
         int index = indexer.index(value);
         FunctionCacheArray local = array.copy(index);
         FunctionCache cache = local.get(index);
         
         if(cache == null) {
            cache = new FunctionCache();
            local.set(index, cache);
            array = local;
         }
         return cache;
      }
   }
}
