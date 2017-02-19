/*
 * ScopeType.java December 2016
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

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeDescription;
import org.snapscript.core.TypeScope;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.function.Function;
import org.snapscript.core.property.Property;

public class ScopeType implements Type {
   
   private final TypeDescription description;
   private final List<Annotation> annotations;
   private final List<Property> properties;
   private final List<Function> functions;
   private final List<Type> types;
   private final Module module;
   private final Scope scope;
   private final Type outer;
   private final String name;
   private final int order;
   
   public ScopeType(Module module, Type outer, String name, int order){
      this.description = new TypeDescription(this);
      this.annotations = new ArrayList<Annotation>();
      this.properties = new ArrayList<Property>();
      this.functions = new ArrayList<Function>();
      this.types = new ArrayList<Type>();
      this.scope = new TypeScope(this);
      this.module = module;
      this.outer = outer;
      this.order = order;
      this.name = name;
   }
   
   @Override
   public List<Annotation> getAnnotations() {
      return annotations;
   }
   
   @Override
   public List<Property> getProperties() {
      return properties;
   }
   
   @Override
   public List<Function> getFunctions(){
      return functions;
   }
   
   @Override
   public List<Type> getTypes(){
      return types;
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
      return null;
   }
   
   @Override
   public Type getOuter(){
      return outer;
   }
   
   @Override
   public Type getEntry(){
      return null;
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
