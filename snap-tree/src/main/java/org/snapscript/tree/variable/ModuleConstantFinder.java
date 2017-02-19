/*
 * ModuleConstantFinder.java December 2016
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

package org.snapscript.tree.variable;

import org.snapscript.core.Module;
import org.snapscript.core.ModuleScopeBinder;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeTraverser;

public class ModuleConstantFinder {
   
   private final ModuleScopeBinder binder;
   private final TypeTraverser finder;
   
   public ModuleConstantFinder() {
      this.binder = new ModuleScopeBinder();
      this.finder = new TypeTraverser();
   }
   
   public Object find(Scope scope, String name) {
      Scope current = binder.bind(scope); // this could be slow
      Module module = current.getModule();
      Type type = module.getType(name);
      Type parent = current.getType();
      
      if(type == null) {
         Object result = module.getModule(name);
         
         if(result == null && parent != null) {
            result = finder.findEnclosing(parent, name);
         }
         return result;
      }
      return type;
   }
}