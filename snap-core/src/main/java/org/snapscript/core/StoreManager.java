/*
 * StoreManager.java December 2016
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

import java.io.InputStream;

import org.snapscript.core.store.CacheStore;
import org.snapscript.core.store.NotFoundException;
import org.snapscript.core.store.Store;

public class StoreManager implements ResourceManager {

   private final CacheStore store;
   
   public StoreManager(Store store) {
      this(store, 100);
   }
   
   public StoreManager(Store store, int capacity) {
      this(store, capacity, 8192);
   }
   
   public StoreManager(Store store, int capacity, int read) {
      this.store = new CacheStore(store, capacity, read);
   }
   
   @Override
   public InputStream getInputStream(String path) {
      try {
         return store.getInputStream(path);
      }catch(NotFoundException e) {
         return null;
      }
   }

   @Override
   public byte[] getBytes(String path) {
      try {
         return store.getBytes(path);
      }catch(NotFoundException e) {
         return null;
      }
   }

   @Override
   public String getString(String path) {
      try {
         return store.getString(path);
      }catch(NotFoundException e) {
         return null;
      }
   }
}
