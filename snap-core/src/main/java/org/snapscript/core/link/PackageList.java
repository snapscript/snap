/*
 * PackageList.java December 2016
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

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Scope;

public class PackageList implements Package {
   
   private final List<Package> modules;
   
   public PackageList(List<Package> modules) {
      this.modules = modules;
   }

   @Override
   public PackageDefinition define(Scope scope) throws Exception {
      List<PackageDefinition> definitions = new ArrayList<PackageDefinition>();
      
      for(Package module : modules){
         PackageDefinition definition = module.define(scope);
         
         if(definition != null) {
            definitions.add(definition);
         }
      }
      return new PackageDefinitionList(definitions);
   }

}
