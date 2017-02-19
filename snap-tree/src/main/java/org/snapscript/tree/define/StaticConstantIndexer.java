/*
 * StaticConstantIndexer.java December 2016
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.snapscript.core.ModifierType;
import org.snapscript.core.Type;
import org.snapscript.core.property.Property;

public class StaticConstantIndexer {
   
   private final String[] reserved;
   
   public StaticConstantIndexer(String... reserved) {
      this.reserved = reserved;
   }
   
   public Set<String> index(Type type) {
      Set<String> names = new HashSet<String>();
      
      if(type != null) {
         List<Property> properties = type.getProperties();

         for(Property property : properties) {
            int modifiers = property.getModifiers();
            String name = property.getName();
            
            if(ModifierType.isStatic(modifiers)) {
               names.add(name);
            }
         }
      }
      for(String name : reserved) {
         names.add(name);
      }
      return names;
   }
}
