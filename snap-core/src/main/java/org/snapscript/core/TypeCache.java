/*
 * TypeCache.java December 2016
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

package org.snapscript.core;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.common.CopyOnWriteSparseArray;
import org.snapscript.common.SparseArray;

public class TypeCache<V> {

   private final SparseArray<V> array;
   private final Cache<Type, V> cache;
   
   public TypeCache() {
      this(1000);
   }
   
   public TypeCache(int capacity) {
      this.array = new CopyOnWriteSparseArray<V>(capacity); // for order > 0
      this.cache = new CopyOnWriteCache<Type, V>(); // for order = 0
   }

   public V take(Type type) {
      int order = type.getOrder();
      
      if(order == 0) {
         return cache.take(type);
      }
      return array.remove(order);
   }

   public V fetch(Type type) {
      int order = type.getOrder();
      
      if(order == 0) {
         return cache.fetch(type);
      }
      return array.get(order);
   }

   public boolean contains(Type type) {
      int order = type.getOrder();
      
      if(order == 0) {
         return cache.contains(type);
      }
      return array.get(order) != null;
   }

   public void cache(Type type, V value) {
      int order = type.getOrder();
      
      if(order == 0) {
         cache.cache(type, value);
      }
      array.set(order, value);
   }
}
