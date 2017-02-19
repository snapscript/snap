/*
 * ImportMatcher.java December 2016
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

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.NameBuilder;
import org.snapscript.core.Path;
import org.snapscript.core.Type;
import org.snapscript.core.TypeNameBuilder;

public class ImportMatcher {

   private final ImportTaskResolver resolver;
   private final NameBuilder builder;
   private final Module parent;
   private final String from;
   
   public ImportMatcher(Module parent, Path path, String from) {
      this.resolver = new ImportTaskResolver(parent, path);
      this.builder = new TypeNameBuilder();
      this.parent = parent;
      this.from = from;
   }

   public Type importType(Set<String> prefixes, String name) throws Exception {
      Context context = parent.getContext();
      ModuleRegistry registry = context.getRegistry();

      for(String prefix : prefixes) {
         Module match = registry.getModule(prefix);
         
         if(match != parent && match != null) {
            Type type = match.getType(name); // get imports from the outer module if it exists

            if(type != null) {
               return type;
            }
         }
      }
      for(String prefix : prefixes) {
         Type type = importType(prefix, name);
         
         if(type != null) {
            return type;
         }
      }
      String type = builder.createFullName(from, name);
      Module module = registry.getModule(type);
      
      if(module == null){ 
         return importType(from, name); // import from current package
      }
      return null;
   }
   
   private Type importType(String prefix, String name) throws Exception {
      String type = builder.createFullName(prefix, name);
      Callable<Type> task = resolver.importType(type);
      
      if(task != null) {
         return task.call();
      }
      return null;
   }
   
   public Module importModule(Set<String> prefixes, String name) throws Exception {
      Context context = parent.getContext();
      ModuleRegistry registry = context.getRegistry();
      
      for(String prefix : prefixes) {
         String inner = builder.createFullName(prefix, name);
         Module match = registry.getModule(inner); // get imports from the outer module if it exists
         
         if(match != null) {
            return match;
         }
      }
      for(String prefix : prefixes) {
         Module module = importModule(prefix, name);
         
         if(module != null) {
            return module;
         }
      }
      return importModule(from, name); // import from current package
   }
   
   private Module importModule(String prefix, String name) throws Exception {
      String type = builder.createFullName(prefix, name);
      Callable<Module> task = resolver.importModule(type);
      
      if(task != null) {
         return task.call();
      }
      return null;
   }
}
