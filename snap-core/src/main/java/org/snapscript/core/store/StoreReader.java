/*
 * StoreReader.java December 2016
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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StoreReader {

   private final Store store;
   private final int read;
   
   public StoreReader(Store store) {
      this(store, 8192);
   }
   
   public StoreReader(Store store, int read) {
      this.store = store;
      this.read = read;
   }
   
   public byte[] getBytes(String path) {
      InputStream source = store.getInputStream(path);
      
      try {
         if(source != null) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream(read);
            byte[] array = new byte[read];
            int count = 0;
            
            while((count = source.read(array)) != -1) {
               buffer.write(array, 0, count);
            }
            return buffer.toByteArray();
         }
      } catch(Exception e) {
         throw new StoreException("Could not read resource '" + path + "'", e);
      }
      return null;
   }
}
