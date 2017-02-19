/*
 * PackageLoader.java December 2016
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

package org.snapscript.core.link;

import java.util.List;

import org.snapscript.core.FilePathConverter;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Path;
import org.snapscript.core.PathConverter;
import org.snapscript.core.ResourceManager;

public class PackageLoader {
   
   private final PackageBundleLoader loader;
   private final PackageBundleMerger merger;
   private final PathConverter converter;
   
   public PackageLoader(PackageLinker linker, ResourceManager manager){
      this.converter = new FilePathConverter();
      this.loader = new PackageBundleLoader(linker, manager, converter);
      this.merger = new PackageBundleMerger();
   }

   public Package load(ImportType type, String... resources) throws Exception {
      PackageBundle bundle = loader.load(resources);
      List<Package> packages = bundle.getPackages();
      
      if(packages.isEmpty() && type.isRequired()) {
         StringBuilder message = new StringBuilder();
         
         for(String resource : resources) {
            Path path = converter.createPath(resource);
            int size = message.length();
            
            if(size > 0) {
               message.append(" or ");
            }
            message.append("'");
            message.append(path);
            message.append("'");
         }
         throw new InternalStateException("Could not load library " + message);
      }
      return merger.merge(bundle);
   }
}
