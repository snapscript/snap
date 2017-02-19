/*
 * TypeExtractor.java December 2016
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

package org.snapscript.core;

import java.util.Set;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;

public class TypeExtractor {
   
   private final Cache<Class, Type> matches;
   private final TypeTraverser traverser;
   private final TypeLoader loader;
   
   public TypeExtractor(TypeLoader loader) {
      this.matches = new CopyOnWriteCache<Class, Type>();
      this.traverser = new TypeTraverser();
      this.loader = loader;
   }
   
   public Type getType(Object value) {
      if(value != null) {
         Class type = value.getClass();
         
         if(Handle.class.isAssignableFrom(type)) {
            Handle handle = (Handle)value;
            return handle.getHandle();
         }
         Type match = matches.fetch(type);
         
         if(match == null) {    
            Type actual = loader.loadType(type);
            
            if(actual != null) {
               matches.cache(type, actual);
            }
            return actual;
         }
         return match;
      }
      return null;
   }

   
   public Set<Type> getTypes(Object value) {
      Type type = getType(value);
      
      if(type != null) {
         return traverser.findHierarchy(type);
      }
      return null;
   }   
   
   public Set<Type> getTypes(Type type) {
      return traverser.findHierarchy(type);
   }
}
