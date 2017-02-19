/*
 * MapResolver.java December 2016
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

import static org.snapscript.core.ModifierType.PUBLIC;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeTraverser;
import org.snapscript.core.Value;
import org.snapscript.core.property.MapProperty;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;

public class MapResolver implements ValueResolver<Map> {
   
   private final AtomicReference<Property> reference;
   private final ObjectResolver resolver;
   private final String name;
   
   public MapResolver(String name) {
      this.reference = new AtomicReference<Property>();
      this.resolver = new ObjectResolver(name);
      this.name = name;
   }
   
   @Override
   public Value resolve(Scope scope, Map left) {
      Property accessor = reference.get();
      
      if(accessor == null) {
         Property match = match(scope, left);
         
         if(match != null) {
            reference.set(match);
            return new PropertyValue(match, left, name);
         }
         return null;
      }
      return new PropertyValue(accessor, left, name);
   }
      
   public Property match(Scope scope, Object left) {
      Property property = resolver.match(scope, left);
   
      if(property == null) {
         Module module = scope.getModule();
         Class type = left.getClass();
         String alias = type.getName();
         Type source = module.getType(alias);
         
         return new MapProperty(name, source, PUBLIC.mask);
      }
      return property;
   }
}