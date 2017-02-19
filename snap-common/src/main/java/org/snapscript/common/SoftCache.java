/*
 * SoftCache.java December 2016
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

package org.snapscript.common;

import java.lang.ref.SoftReference;
import java.util.Set;

public class SoftCache<K, V> implements Cache<K, V> {
   
   private final Cache<K, SoftReference<V>> cache;

   public SoftCache() {
      this(100);
   }
   
   public SoftCache(int capacity) {
      this.cache = new LeastRecentlyUsedCache<K, SoftReference<V>>(capacity);
   }

   @Override
   public Set<K> keySet() {
      return cache.keySet();
   }

   @Override
   public V take(K key) {
      SoftReference<V> reference = cache.take(key);
      
      if(reference != null) {
         return reference.get();
      }
      return null;
   }

   @Override
   public V fetch(K key) {
      SoftReference<V> reference = cache.fetch(key);
      
      if(reference != null) {
         return reference.get();
      }
      return null;
   }
   
   @Override
   public void cache(K key, V value) {
      SoftReference<V> reference = new SoftReference<V>(value);
      
      if(value != null) {
         cache.cache(key, reference);
      }
   }
   
   @Override
   public boolean contains(K key) {
      SoftReference<V> reference = cache.fetch(key);
      
      if(reference != null) {
         return reference.get() != null;
      }
      return false;
   }

   @Override
   public boolean isEmpty() {
      return cache.isEmpty();
   }


   @Override
   public void clear() {
      cache.clear();
   }

   @Override
   public int size() {
      return cache.size();
   }

}
