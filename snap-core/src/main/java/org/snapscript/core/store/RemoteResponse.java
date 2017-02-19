/*
 * RemoteResponse.java December 2016
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
import java.net.HttpURLConnection;

public class RemoteResponse {
   
   private final HttpURLConnection request;
   private final RemoteStatus status;
   private final String resource;
   
   public RemoteResponse(HttpURLConnection request, RemoteStatus status, String resource) {
      this.resource = resource;
      this.request = request;
      this.status = status;
   }
   
   public RemoteStatus getStatus() {
      try {
         return status;
      } catch(Exception e) {
         throw new StoreException("Could not determine status for " + resource, e);
      }
   }
   
   public InputStream getInputStream(){
      try {
         return request.getInputStream();
      } catch(Exception e) {
         throw new StoreException("Could not get input for " + resource, e);
      }
   }
   
   public OutputStream getOutputStream() {
      try {
         return request.getOutputStream();
      } catch(Exception e) {
         throw new StoreException("Could not get output for " + resource, e);
      }
   }
}
