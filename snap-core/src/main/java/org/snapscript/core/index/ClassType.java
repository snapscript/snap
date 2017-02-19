/*
 * ClassType.java December 2016
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

import org.snapscript.core.TypeDescription;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeScope;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.function.Function;
import org.snapscript.core.property.Property;

public class ClassType implements Type {

   private final TypeDescription description;
   private final ClassIndex index;
   private final Scope scope;
   private final Class type;
   private final String name;
   private final int order;
   
   public ClassType(ClassIndexer indexer, Class type, String name, int order) {
      this.description = new TypeDescription(this);
      this.index = new ClassIndex(indexer, this);
      this.scope = new TypeScope(this);
      this.name = name;
      this.type = type;
      this.order = order;
   }
   
   @Override
   public List<Annotation> getAnnotations() {
      return index.getAnnotations();
   }
   
   @Override
   public List<Property> getProperties() {
      return index.getProperties();
   }

   @Override
   public List<Function> getFunctions() {
      return index.getFunctions();
   }

   @Override
   public List<Type> getTypes() {
      return index.getTypes();
   }

   @Override
   public Module getModule() {
      return index.getModule();
   }
   
   @Override
   public Type getOuter() {
      return index.getOuter();
   }

   @Override
   public Type getEntry() {
      return index.getEntry();
   }
   
   @Override
   public Scope getScope() {
      return scope;
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public Class getType() {
      return type;
   }
   
   @Override
   public int getOrder(){
      return order;
   }
   
   @Override
   public String toString() {
      return description.toString();
   }

}
