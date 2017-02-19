/*
 * LeastRecentlyUsedSet.java December 2016
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

import java.util.AbstractSet;
import java.util.Iterator;

public class LeastRecentlyUsedSet<T> extends AbstractSet<T> {

   private final LeastRecentlyUsedMap<T, T> cache;
   
   public LeastRecentlyUsedSet() {
      this(1000);
   }
   
   public LeastRecentlyUsedSet(int capacity) {
      this.cache = new LeastRecentlyUsedMap<T, T>(capacity);
   }
 
   @Override
   public boolean contains(Object value) {
      return cache.containsKey(value);
   }
   
   @Override
   public boolean remove(Object value) {
      return cache.remove(value) != null;
   }
   
   @Override
   public boolean add(T value) {
      return cache.put(value,  value) != null;
   }
   
   @Override
   public Iterator<T> iterator() {
      return cache.keySet().iterator();
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