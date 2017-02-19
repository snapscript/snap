/*
 * RemoteClient.java December 2016
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

import static org.snapscript.core.store.RemoteStatus.ERROR;
import static org.snapscript.core.store.RemoteStatus.NOT_FOUND;
import static org.snapscript.core.store.RemoteStatus.SUCCESS;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class RemoteClient {
   
   private static final String WRITE_METHOD = "PUT";
   private static final int SUCCESS_CODE = 200;
   private static final int NOT_FOUND_CODE = 404;
   
   private final RemoteLocation location;
   private final URI root;
   
   public RemoteClient(URI root) {
      this.location = new RemoteLocation(root);
      this.root = root;
   }

   public RemoteResponse get(String path) {
      try {
         URL address = location.createRelative(path);
         URLConnection connection = address.openConnection();
         HttpURLConnection request = (HttpURLConnection)connection;
         int code = request.getResponseCode();
         
         if(code == SUCCESS_CODE) {
            return new RemoteResponse(request, SUCCESS, path);
         }
         if(code == NOT_FOUND_CODE) {
            return new RemoteResponse(request, NOT_FOUND, path);
         }
         return new RemoteResponse(request, ERROR, path);
      } catch(Exception e) {
         throw new StoreException("Could not load resource '" + path + "' from '" + root + "'", e);
      }
   }
   
   public RemoteResponse put(String path) {
      try {
         URL address = location.createRelative(path);
         URLConnection connection = address.openConnection();
         HttpURLConnection request = (HttpURLConnection)connection;
         
         request.setDoOutput(true);
         request.setRequestMethod(WRITE_METHOD);
         request.connect(); // check if the server is up
         
         return new RemoteResponse(request, SUCCESS, path);
      } catch(Exception e) {
         throw new StoreException("Could not write resource '" + path + "' to '" + root + "'", e);
      } 
   }
}
