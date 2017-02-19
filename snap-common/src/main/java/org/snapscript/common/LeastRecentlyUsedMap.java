/*
 * LeastRecentlyUsedMap.java December 2016
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

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class LeastRecentlyUsedMap<K, V> extends LinkedHashMap<K, V> {

   private final RemovalListener<K, V> removalListener;
   private final int capacity;

   public LeastRecentlyUsedMap() {
      this(null);
   }

   public LeastRecentlyUsedMap(int capacity) {
      this(null, capacity);
   }

   public LeastRecentlyUsedMap(RemovalListener<K, V> removalListener) {
      this(removalListener, 100);
   }

   public LeastRecentlyUsedMap(RemovalListener<K, V> removalListener, int capacity) {
      this.removalListener = removalListener;
      this.capacity = capacity;
   }

   @Override
   protected boolean removeEldestEntry(Entry<K, V> eldest) {
      int size = size();

      if (size <= capacity) {
         return false;
      }
      if (removalListener != null) {
         V value = eldest.getValue();
         K key = eldest.getKey();

         removalListener.notifyRemoved(key, value);
      }
      return true;
   }

   public static interface RemovalListener<K, V> {
      public void notifyRemoved(K key, V value);
   }
}
