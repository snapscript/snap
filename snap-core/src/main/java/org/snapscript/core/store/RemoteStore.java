/*
 * RemoteStore.java December 2016
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

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public class RemoteStore implements Store {
   
   private final RemoteClient client;
   private final URI root;
   
   public RemoteStore(URI root) {
      this.client = new RemoteClient(root);
      this.root = root;
   }
   
   @Override
   public InputStream getInputStream(String path) {  
      RemoteResponse response = client.get(path);
      RemoteStatus status = response.getStatus();
      
      if(status.isNotFound()) {
         throw new NotFoundException("Could not find resource '" + path + "' from '" + root + "'");
      }
      if(status.isError()) {
         throw new StoreException("Error reading resource '" + path + "' from '" + root + "'");
      }
      return response.getInputStream();
   }

   @Override
   public OutputStream getOutputStream(String path) {
      RemoteResponse response = client.put(path);
      RemoteStatus status = response.getStatus();
      
      if(status.isError()) {
         throw new StoreException("Error writing resource '" + path + "' to '" + root + "'");
      }
      return response.getOutputStream();
   }
}
