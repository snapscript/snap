/*
 * CacheStore.java December 2016
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

package org.snapscript.core.store;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import org.snapscript.common.Cache;
import org.snapscript.common.LeastRecentlyUsedSet;
import org.snapscript.common.SoftCache;

public class CacheStore implements Store {

   private final Cache<String, byte[]> cache;
   private final Set<String> failures;
   private final StoreReader reader;
   private final Store store;
   
   public CacheStore(Store store) {
      this(store, 100);
   }
   
   public CacheStore(Store store, int capacity) {
      this(store, capacity, 8192);
   }
   
   public CacheStore(Store store, int capacity, int read) {
      this.failures = new LeastRecentlyUsedSet<String>(capacity);
      this.cache = new SoftCache<String, byte[]>(capacity);
      this.reader = new StoreReader(store, read);
      this.store = store;
   }
   
   public byte[] getBytes(String path) {
      if(!failures.contains(path)) {
         byte[] resource = cache.fetch(path);
         
         if(resource == null) {
            try {
               resource = reader.getBytes(path);
               cache.cache(path, resource);
            } catch(NotFoundException cause) {
               failures.add(path);
               throw cause;
            }
         }
         if(resource == null) {
            throw new NotFoundException("Could not find '" + path + "'");
         }
         return resource;
      }
      return null;
   }
   
   public String getString(String path) {
      byte[] resource = getBytes(path);
      
      try {
         if(resource != null) {
            return new String(resource, "UTF-8");
         }
      }catch(Exception e) {
         throw new StoreException("Could not read resource '" + path + "'", e);
      }
      return null;
   }
   
   @Override
   public InputStream getInputStream(String path) {
      byte[] resource = getBytes(path);
      
      try {
         if(resource != null) {
            return new ByteArrayInputStream(resource);
         }
      }catch(Exception e) {
         throw new StoreException("Could not read resource '" + path + "'", e);
      }
      return null;
   }
   
   @Override
   public OutputStream getOutputStream(String path) {
      return store.getOutputStream(path);
   }
}
