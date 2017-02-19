/*
 * RemoteLocation.java December 2016
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

import java.net.URI;
import java.net.URL;

public class RemoteLocation {
   
   private final URI root;
   
   public RemoteLocation(URI root) {
      this.root = root;
   }
   
   public URL createRelative(String path) throws Exception {
      String original = root.getPath();
      String scheme = root.getScheme();
      String host = root.getHost();
      int port = root.getPort();
      
      if(!original.endsWith("/")) {
         original = original + "/";
      }
      if(path.startsWith("/")) {
         path = path.substring(1);
      }
      return new URL(scheme + "://" +  host + ":" + port + original + path);
   }
}
