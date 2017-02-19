/*
 * ClassHierarchyIndexer.java December 2016
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

package org.snapscript.core.index;

import static org.snapscript.core.Reserved.ANY_TYPE;
import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Type;

public class ClassHierarchyIndexer {

   private final TypeIndexer indexer;
   
   public ClassHierarchyIndexer(TypeIndexer indexer) {
      this.indexer = indexer;
   }
   
   public List<Type> index(Class source) throws Exception {
      List<Type> hierarchy = new ArrayList<Type>();
      
      if(source == Object.class) {
         Type base = indexer.defineType(DEFAULT_PACKAGE, ANY_TYPE);
         
         if(base != null) {
            hierarchy.add(base);
         }
      } else {
         Class[] interfaces = source.getInterfaces();
         Class base = source.getSuperclass(); // the super class
         
         if(base != null) {
            Type type = indexer.loadType(base); // the super type
         
            if(type != null) {
               hierarchy.add(type);
            }
         }
         for (Class entry : interfaces) {
            Type type = indexer.loadType(entry);
            
            if(type != null) {
               hierarchy.add(type);
            }
         }
      }
      return hierarchy;
   }
}
