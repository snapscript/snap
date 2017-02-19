/*
 * AnyInstanceBuilder.java December 2016
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

import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;

import org.snapscript.core.Context;
import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Instance;
import org.snapscript.core.define.PrimitiveInstance;

public class AnyInstanceBuilder {
   
   private Module module;
   
   public AnyInstanceBuilder() {
      super();
   }

   public Instance create(Scope scope, Type real) throws Exception {
      Scope inner = real.getScope();
      Model model = scope.getModel();
      
      if(module == null) {
         Module parent = scope.getModule();
         Context context = parent.getContext();
         ModuleRegistry registry = context.getRegistry();
         
         module = registry.addModule(DEFAULT_PACKAGE);
      }
      return new PrimitiveInstance(module, model, inner, real); 
   }
}
