/*
 * TypeLoader.java December 2016
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

import org.snapscript.core.extend.ClassExtender;
import org.snapscript.core.index.TypeIndexer;
import org.snapscript.core.link.ImportScanner;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageLinker;
import org.snapscript.core.link.PackageLoader;
import org.snapscript.core.link.PackageManager;

public class TypeLoader {
   
   private final PackageManager manager;
   private final PackageLoader loader;
   private final ImportScanner scanner;
   private final TypeIndexer indexer;
   private final ClassExtender extender;
   
   public TypeLoader(PackageLinker linker, ModuleRegistry registry, ResourceManager manager){
      this.scanner = new ImportScanner();
      this.extender = new ClassExtender(this);
      this.indexer = new TypeIndexer(registry, scanner, extender);
      this.loader = new PackageLoader(linker, manager);
      this.manager = new PackageManager(loader, scanner);
   }
   
   public Package importPackage(String module)  {
      return manager.importPackage(module);
   }   
   
   public Package importType(String type) {
      return manager.importType(type);  // import a runtime
   }
   
   public Package importType(String module, String name) {
      return manager.importType(module, name); 
   }
   
   public Type defineType(String module, String name) {
      return indexer.defineType(module, name);
   }
   
   public Type resolveType(String module, String name) {
      return indexer.loadType(module, name);
   }
   
   public Type resolveType(String module, String name, int size) {
      return indexer.loadType(module, name, size);
   }
   
   public Type resolveType(String type) {
      return indexer.loadType(type);
   }
   
   public Type loadType(Class type) {
      return indexer.loadType(type);
   } 
}
