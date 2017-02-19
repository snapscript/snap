/*
 * ScopeResolver.java December 2016
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

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.Value;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.PropertyValue;

public class ScopeResolver implements ValueResolver<Scope> {
   
   private final AtomicReference<Property> reference;
   private final String name;
   
   public ScopeResolver(String name) {
      this.reference = new AtomicReference<Property>();
      this.name = name;
   }
   
   @Override
   public Value resolve(Scope scope, Scope left) {
      State state = left.getState();
      Value value = state.get(name);
      
      if(value == null) {
         Type type = left.getType();
         
         if(type != null) {
            Property property = match(scope, left);
            
            if(property != null) {
               reference.set(property);
               return new PropertyValue(property, left, name);
            }
         }
      }
      return value;
   }
   
   private Property match(Scope scope, Scope left) {
      Property property = reference.get();
      
      if(property == null) {
         Type source = left.getType();
         Module module = scope.getModule();
         Context context = module.getContext();
         TypeExtractor extractor = context.getExtractor();
         Set<Type> list = extractor.getTypes(source);
         
         for(Type base : list) {
            Property match = match(scope, left, base);
            
            if(match != null) {
               return match;
            }
         }
      }
      return property;
   }
   
   private Property match(Scope scope, Object left, Type type) {
      List<Property> properties = type.getProperties();
      
      for(Property property : properties){
         String field = property.getName();
         
         if(field.equals(name)) {
            return property;
         }
      } 
      return null;
   }
}