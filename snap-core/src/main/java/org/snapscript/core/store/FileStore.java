/*
 * FileStore.java December 2016
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStore implements Store {

   private final ClassPathStore store;
   private final File root;
   
   public FileStore(File root) {
      this.store = new ClassPathStore();
      this.root = root;
   }
   
   @Override
   public InputStream getInputStream(String path) {
      File resource = new File(root, path);
      
      if(!resource.exists()) {
         return store.getInputStream(path);
      }
      try {
         return new FileInputStream(resource);
      } catch(Exception e) {
         throw new StoreException("Could not read resource '" + path + "'", e);
      }
   }

   @Override
   public OutputStream getOutputStream(String path) {
      File resource = new File(root, path);
      
      if(resource.exists()) {
         resource.delete();
      }
      File parent = resource.getParentFile();
      
      if(parent.exists()) {
         parent.mkdirs();
      }
      try {
         return new FileOutputStream(resource);
      } catch(Exception e) {
         throw new StoreException("Could not write resource '" + path + "'", e);
      }
   }
}
