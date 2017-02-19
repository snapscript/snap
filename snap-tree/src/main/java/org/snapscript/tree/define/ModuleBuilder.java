/*
 * ModuleBuilder.java December 2016
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

package org.snapscript.tree.define;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.TypeNameBuilder;
import org.snapscript.core.link.ImportManager;
import org.snapscript.tree.NameExtractor;
import org.snapscript.tree.annotation.AnnotationList;

public class ModuleBuilder {

   private final AnnotationList annotations;
   private final TypeNameBuilder builder;
   private final NameExtractor extractor;
   
   public ModuleBuilder(AnnotationList annotations, ModuleName module) {
      this.extractor = new NameExtractor(module);
      this.builder = new TypeNameBuilder();
      this.annotations = annotations;
   }

   public Module create(Scope scope) throws Exception {
      String name = extractor.extract(scope);
      Module parent = scope.getModule();
      Module module = create(parent, name);
      ImportManager manager = module.getManager();
      String include = parent.getName();
      
      annotations.apply(scope, module);
      manager.addImport(include); // make outer classes accessible
      
      return module;
   }
   
   protected Module create(Module parent, String name) throws Exception {
      Path path = parent.getPath();
      String prefix = parent.getName();
      String type = builder.createFullName(prefix, name);
      Context context = parent.getContext();
      ImportManager manager = parent.getManager();
      ModuleRegistry registry = context.getRegistry();
      Module module = registry.addModule(type, path); // create module
      
      manager.addImports(module); // add parent imports
      manager.addImport(type, name); // make module accessible by name
      
      return module;
   }
}
