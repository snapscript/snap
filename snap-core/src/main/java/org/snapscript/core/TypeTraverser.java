/*
 * TypeTraverser.java December 2016
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

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class TypeTraverser {
   
   private final TypeCache<Set<Type>> types;
   
   public TypeTraverser() {
      this.types = new TypeCache<Set<Type>>();
   }

   public Set<Type> findHierarchy(Type type) {
      Set<Type> list = types.fetch(type);
      
      if(list == null) {
         list = findHierarchy(type, type);
         types.cache(type, list);
      }
      return list;
   }
   
   private Set<Type> findHierarchy(Type root, Type type) {
      Set<Type> list = new LinkedHashSet<Type>();
      
      if(type != null) {
         findHierarchy(root, type, list);
      }
      return Collections.unmodifiableSet(list);
   }
   
   private Set<Type> findHierarchy(Type root, Type type, Set<Type> list) {
      List<Type> types = type.getTypes();
      
      if(list.add(type)) {
         for(Type entry : types) {
            if(entry == root) { 
               throw new InternalStateException("Hierarchy for '" + type + "' contains a cycle");
            }
            findHierarchy(root, entry, list);
         }
      }
      return list;
   }
   
   public Type findEnclosing(Type type, String name) {
      Set<Type> done = new LinkedHashSet<Type>();
      
      if(type != null) {
         return findEnclosing(type, name, done);
      }
      return null;
   }
   
   private Type findEnclosing(Type type, String name, Set<Type> done) {
      Module module = type.getModule();
      
      while(type != null){ // search outer classes
         String prefix = type.getName();
         Type result = module.getType(prefix + "$"+name);
         
         if(result == null) {
            result = findHierarchy(type, name, done);
         }
         if(result != null) {
            return result;
         }
         type = type.getOuter();
      }
      return null;
   }
   
   private Type findHierarchy(Type type, String name, Set<Type> done) {
      List<Type> types = type.getTypes(); // do not use extractor here
      
      for(Type base : types) {
         if(done.add(base)) { // avoid loop
            Type result = findEnclosing(base, name, done);
            
            if(result != null) {
               return result;
            }
         }
      }
      return null;
   }
}
