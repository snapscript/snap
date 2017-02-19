/*
 * ScopeMerger.java December 2016
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

package org.snapscript.core;

public class ScopeMerger {

   private final PathConverter converter;
   private final Context context;
   
   public ScopeMerger(Context context) {
      this.converter = new FilePathConverter();
      this.context = context;
   }
   
   public Scope merge(Model model, String name) {
      Path path = converter.createPath(name);
      
      if(path == null) {
         throw new InternalStateException("Module '" +name +"' does not have a path"); 
      }
      return merge(model, name, path);
   }
   
   public Scope merge(Model model, String name, Path path) {
      ModuleRegistry registry = context.getRegistry();
      Module module = registry.addModule(name, path);
      
      if(module == null) {
         throw new InternalStateException("Module '" +name +"' not found");
      }
      return new ModelScope(model, module);
   }
}
