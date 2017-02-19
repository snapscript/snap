/*
 * ScopeArrayType.java December 2016
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

import java.util.Collections;
import java.util.List;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeDescription;
import org.snapscript.core.TypeScope;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.function.Function;
import org.snapscript.core.property.Property;

public class ScopeArrayType implements Type {
   
   private static final Class[] ARRAYS = {
      Object.class,
      Object[].class,
      Object[][].class,
      Object[][][].class,
   };
   
   private final TypeDescription description;
   private final Module module;
   private final Scope scope;
   private final Type entry;
   private final String name;
   private final int order;
   private final int size;
   
   public ScopeArrayType(Module module, String name, Type entry, int size, int order){
      this.description = new TypeDescription(this);
      this.scope = new TypeScope(this);
      this.module = module;
      this.order = order;
      this.entry = entry;
      this.name = name;
      this.size = size;
   }
   
   @Override
   public List<Annotation> getAnnotations() {
      return Collections.emptyList();
   }
   
   @Override
   public List<Property> getProperties() {
      return Collections.emptyList();
   }
   
   @Override
   public List<Function> getFunctions(){
      return Collections.emptyList();
   }
   
   @Override
   public List<Type> getTypes(){
      return Collections.emptyList();
   }
   
   @Override
   public Module getModule(){
      return module;
   }
   
   @Override
   public Scope getScope(){
      return scope;
   }
   
   @Override
   public String getName(){
      return name;
   }
   
   @Override
   public Class getType() {
      return ARRAYS[size];
   }
   
   @Override
   public Type getOuter(){
      return null;
   }
   
   @Override
   public Type getEntry(){
      return entry;
   }
   
   @Override
   public int getOrder() {
      return order;
   }
   
   @Override
   public String toString() {
      return description.toString();
   }
}
