/*
 * ExtensionRegistry.java December 2016
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

package org.snapscript.core.extend;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.snapscript.core.InternalException;
import org.snapscript.core.Module;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.function.Function;

public class ExtensionRegistry {

   private final Map<Class, Class> extensions;
   private final FunctionExtractor extractor;
   private final TypeLoader loader;
   
   public ExtensionRegistry(TypeLoader loader){
      this.extensions = new ConcurrentHashMap<Class, Class>();
      this.extractor = new FunctionExtractor(loader);
      this.loader = loader;
   }
   
   public void register(Class type, Class extension) {
      extensions.put(type, extension);
   }
   
   public List<Function> extract(Class type) {
      Class extension = extensions.get(type);
      
      if(extension != null) {
         try {
            Object instance = extension.newInstance();
            Type match = loader.loadType(type);
            Module module = match.getModule();
            
            return extractor.extract(module, type, instance);
         } catch(Exception e) {
            throw new InternalException("Could not extend " + type, e);
         }
      }
      return Collections.emptyList();
   }
}
