/*
 * ClassIndexer.java December 2016
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

import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;

import java.util.List;

import org.snapscript.core.InternalArgumentException;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.PrimitivePromoter;
import org.snapscript.core.Type;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.annotation.AnnotationExtractor;
import org.snapscript.core.extend.ClassExtender;
import org.snapscript.core.function.Function;
import org.snapscript.core.link.ImportScanner;
import org.snapscript.core.property.Property;

public class ClassIndexer {

   private final ClassHierarchyIndexer hierarchy;
   private final AnnotationExtractor extractor;
   private final FunctionIndexer functions;
   private final PropertyIndexer properties;
   private final PrimitivePromoter promoter;
   private final ImportScanner scanner;
   private final ModuleRegistry registry;
   private final TypeIndexer indexer;

   public ClassIndexer(TypeIndexer indexer, ModuleRegistry registry, ImportScanner scanner, ClassExtender extender) {
      this.hierarchy = new ClassHierarchyIndexer(indexer);
      this.properties = new PropertyIndexer(indexer);
      this.functions = new FunctionIndexer(indexer, extender);
      this.extractor = new AnnotationExtractor();
      this.promoter = new PrimitivePromoter();
      this.scanner = scanner;
      this.registry = registry;
      this.indexer = indexer;
   }
   
   public List<Type> indexTypes(ClassType type) throws Exception {
      Class source = type.getType();
      Class actual = promoter.promote(source);
      
      if(actual == null) {
         throw new InternalArgumentException("Could not determine type for " + source);
      }
      return hierarchy.index(actual);
   }
   
   public List<Annotation> indexAnnotations(ClassType type) throws Exception {
      Class source = type.getType();
      Class actual = promoter.promote(source);
      
      if(actual == null) {
         throw new InternalArgumentException("Could not determine type for " + source);
      }
      return extractor.extract(actual);
   }
   
   public List<Property> indexProperties(ClassType type) throws Exception {
      Class source = type.getType();
      Class actual = promoter.promote(source);
      
      if(actual == null) {
         throw new InternalArgumentException("Could not determine type for " + source);
      }
      return properties.index(actual);
   }
   
   public List<Function> indexFunctions(ClassType type) throws Exception {
      Class source = type.getType();
      Class actual = promoter.promote(source);
      
      if(actual == null) {
         throw new InternalArgumentException("Could not determine type for " + source);
      }
      return functions.index(type);
   }
   
   public Module indexModule(ClassType type) throws Exception {
      Class source = type.getType();
      Class actual = promoter.promote(source);
      
      if(actual == null) {
         throw new InternalArgumentException("Could not determine type for " + source);
      }
      while(actual.isArray()) {
         actual = actual.getComponentType();
      }
      Package module = actual.getPackage();
      
      if(module != null) {
         String name = scanner.importName(module);
         
         if(name != null) {
            return registry.addModule(name);
         }
      }
      return registry.addModule(DEFAULT_PACKAGE);
   }
   
   public Type indexOuter(ClassType type) throws Exception {
      Class source = type.getType();
      Class outer = source.getEnclosingClass();
      
      if(outer != null) {
         Class actual = promoter.promote(outer);
         
         if(actual == null) {
            throw new InternalArgumentException("Could not determine type for " + source);
         }
         return indexer.loadType(actual);
      }
      return null;
   }
   
   public Type indexEntry(ClassType type) throws Exception {
      Class source = type.getType();
      Class entry = source.getComponentType();
      
      if(entry != null) {
         Class actual = promoter.promote(entry);
         
         if(actual == null) {
            throw new InternalArgumentException("Could not determine type for " + source);
         }
         return indexer.loadType(actual);
      }
      return null;
   }
}
