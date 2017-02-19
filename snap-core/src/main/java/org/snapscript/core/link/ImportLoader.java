/*
 * ImportLoader.java December 2016
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

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Any;
import org.snapscript.core.ContextClassLoader;
import java.lang.Package;

public class ImportLoader {
   
   private final Cache<String, Package> packages;
   private final Cache<String, Class> types;
   private final ClassLoader loader;
   
   public ImportLoader() {
      this.packages = new CopyOnWriteCache<String, Package>();
      this.types = new CopyOnWriteCache<String, Class>();
      this.loader = new ContextClassLoader(Any.class);
   }
   
   public Package loadPackage(String name) {
      try {
         if(!packages.contains(name)) {
            Package match = Package.getPackage(name);
            
            packages.cache(name, match);
         }
      }catch(Exception e) {
         packages.cache(name, null);
         return null;
      }
      return packages.fetch(name);
   }
   
   public Class loadClass(String name) {
      try {
         if(!types.contains(name)) {
            Class match = loader.loadClass(name);
            
            types.cache(name, match);
         }
      } catch(Exception e) {
         types.cache(name, null);
         return null;
      }
      return types.fetch(name);
      
   }
}
