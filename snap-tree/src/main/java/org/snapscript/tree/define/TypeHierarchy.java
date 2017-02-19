/*
 * TypeHierarchy.java December 2016
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

import java.util.List;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.tree.reference.TraitReference;
import org.snapscript.tree.reference.TypeReference;

public class TypeHierarchy {
   
   private final AnyDefinition definition;
   private final TraitReference[] traits; 
   private final TypeReference name;

   public TypeHierarchy(TraitReference... traits) {
      this(null, traits);     
   }
   
   public TypeHierarchy(TypeReference name, TraitReference... traits) {
      this.definition = new AnyDefinition();
      this.traits = traits;
      this.name = name;
   }

   public void update(Scope scope, Type type) throws Exception {
      List<Type> types = type.getTypes();
      
      if(name != null) {
         Value value = name.evaluate(scope, null);
         Type base = value.getValue();
         
         if(base != null) {
            types.add(base);
         }
      }else {
         Type base = definition.create(scope);
         
         if(base != null) {
            types.add(base);
         }
      }
      for(int i = 0; i < traits.length; i++) {
         Value value = traits[i].evaluate(scope, null);
         Type trait = value.getValue();
         
         if(trait != null) {
            types.add(trait);
         }
      }
   }

}
