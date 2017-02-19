/*
 * ImportTaskResolver.java December 2016
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

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;

import org.snapscript.core.Context;
import org.snapscript.core.FilePathConverter;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.Path;
import org.snapscript.core.PathConverter;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;

public class ImportTaskResolver {

   private final PathConverter converter;
   private final Set<Path> imports;
   private final Module parent;
   private final Path from;
   
   public ImportTaskResolver(Module parent, Path from) {
      this.imports = new CopyOnWriteArraySet<Path>();
      this.converter = new FilePathConverter();
      this.parent = parent;
      this.from = from;
   }
   
   public Callable<Type> importType(String name) throws Exception{
      try {
         Context context = parent.getContext();
         TypeLoader loader = context.getLoader();
         Package module = loader.importType(name);
         Path path = converter.createPath(name);
         
         return new TypeImport(loader, module, path, name); // import exceptions will propagate
      } catch(Exception e) {
         return null;
      }
   }
   
   public Callable<Module> importModule(String name) throws Exception{
      try {
         Context context = parent.getContext();
         TypeLoader loader = context.getLoader();
         ModuleRegistry registry = context.getRegistry();
         Package module = loader.importType(name);
         Path path = converter.createPath(name);
         
         return new ModuleImport(registry, module, path, name); // import exceptions will propagate
      } catch(Exception e) {
         return null;
      }
   }
   
   private class TypeImport implements Callable<Type> {
      
      private final TypeLoader loader;
      private final Package module;
      private final String name;
      private final Path path;
      
      public TypeImport(TypeLoader loader, Package module, Path path, String name){
         this.loader = loader;
         this.module = module;
         this.name = name;
         this.path = path;
      }

      @Override
      public Type call() {
         try {
            if(!imports.contains(path)) {
               Scope scope = parent.getScope();
               PackageDefinition definition = module.define(scope);
               Statement statement = definition.compile(scope, from);
            
               statement.execute(scope); 
               imports.add(path);
            }
         } catch(Exception e) {
            throw new InternalStateException("Could not import '" + path+"'", e);
         }
         return loader.resolveType(name);
      }
   }
   
   private class ModuleImport implements Callable<Module> {
      
      private final ModuleRegistry registry;
      private final Package module;
      private final String name;
      private final Path path;
      
      public ModuleImport(ModuleRegistry registry, Package module, Path path, String name){
         this.registry = registry;
         this.module = module;
         this.name = name;
         this.path = path;
      }

      @Override
      public Module call() {
         try {
            if(!imports.contains(path)) {
               Scope scope = parent.getScope();
               PackageDefinition definition = module.define(scope);
               Statement statement = definition.compile(scope, from);
               
               statement.execute(scope); 
               imports.add(path);
            }
         } catch(Exception e) {
            throw new InternalStateException("Could not import '" + path+"'", e);
         }
         return registry.getModule(name);
      }
   }
}
