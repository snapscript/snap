/*
 * FunctionCache.java December 2016
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

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.function.Function;

public class FunctionCache {

   private final Cache<Object, Function> cache;
   
   public FunctionCache() {
      this.cache = new CopyOnWriteCache<Object, Function>();
   }
   
   public boolean contains(Object key) {
      return cache.contains(key);
   }
   
   public Function fetch(Object key) {
      return cache.fetch(key);
   }
   
   public void cache(Object key, Function function) {
      cache.cache(key, function);
   }
}
