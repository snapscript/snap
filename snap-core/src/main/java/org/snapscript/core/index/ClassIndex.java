/*
 * ClassIndex.java December 2016
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

import java.util.List;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Type;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.function.Function;
import org.snapscript.core.property.Property;

public class ClassIndex {
   
   private List<Annotation> annotations;
   private List<Property> properties;
   private List<Function> functions;
   private ClassIndexer indexer;
   private List<Type> types;
   private ClassType require;
   private Module module;
   private Type outer;
   private Type entry;
   
   public ClassIndex(ClassIndexer indexer, ClassType require) {      
      this.indexer = indexer;
      this.require = require;
   }
   
   public List<Annotation> getAnnotations() {
      if(annotations == null) {
         try {
            annotations = indexer.indexAnnotations(require);
         } catch(Exception e) {
            throw new InternalStateException("Could not index " + require, e);
         }
      }
      return annotations;
   }

   public List<Property> getProperties() {
      if(properties == null) {
         try {
            properties = indexer.indexProperties(require);
         } catch(Exception e) {
            throw new InternalStateException("Could not index " + require, e);
         }
      }
      return properties;
   }
   
   public List<Function> getFunctions() {
      if(functions == null) {
         try {
            functions = indexer.indexFunctions(require);
         } catch(Exception e) {
            throw new InternalStateException("Could not index " + require, e);
         }
      }
      return functions;
   }
   
   public List<Type> getTypes() {
      if(types == null) {
         try {
            types = indexer.indexTypes(require);
         } catch(Exception e) {
            throw new InternalStateException("Could not index " + require, e);
         }
      }
      return types;
   }
   
   public Module getModule() {
      if(module == null) {
         try {
            module = indexer.indexModule(require);
         } catch(Exception e) {
            throw new InternalStateException("Could not index " + require, e);
         }
      }
      return module;
   }
   
   public Type getOuter() {
      if(entry == null) {
         try {
            entry = indexer.indexOuter(require);
         } catch(Exception e) {
            throw new InternalStateException("Could not index " + require, e);
         }
      }
      return entry;
   }

   public Type getEntry() {
      if(entry == null) {
         try {
            entry = indexer.indexEntry(require);
         } catch(Exception e) {
            throw new InternalStateException("Could not index " + require, e);
         }
      }
      return entry;
   }
}
