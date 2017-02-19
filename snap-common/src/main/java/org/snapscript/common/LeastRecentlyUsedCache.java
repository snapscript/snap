/*
 * LeastRecentlyUsedCache.java December 2016
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

import java.util.Map;
import java.util.Set;

import org.snapscript.common.LeastRecentlyUsedMap.RemovalListener;

public class LeastRecentlyUsedCache<K, V> implements Cache<K, V> {

   private final Map<K, V> cache;

   public LeastRecentlyUsedCache() {
      this(null);
   }

   public LeastRecentlyUsedCache(int capacity) {
      this(null, capacity);
   }

   public LeastRecentlyUsedCache(RemovalListener<K, V> removalListener) {
      this(removalListener, 100);
   }

   public LeastRecentlyUsedCache(RemovalListener<K, V> removalListener, int capacity) {
      this.cache = new LeastRecentlyUsedMap<K, V>(removalListener, capacity);
   }

   @Override
   public synchronized void clear() {
      cache.clear();
   }

   @Override
   public synchronized int size() {
      return cache.size();
   }

   @Override
   public synchronized Set<K> keySet() {
      return cache.keySet();
   }

   @Override
   public synchronized V fetch(K key) {
      return cache.get(key);
   }

   @Override
   public synchronized void cache(K key, V value) {
      cache.put(key, value);
   }

   @Override
   public synchronized V take(K key) {
      return cache.remove(key);
   }

   @Override
   public synchronized boolean contains(K key) {
      return cache.containsKey(key);
   }

   @Override
   public synchronized boolean isEmpty() {
      return cache.isEmpty();
   }
}
