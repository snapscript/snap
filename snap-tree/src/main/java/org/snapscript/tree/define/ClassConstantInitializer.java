/*
 * ClassConstantInitializer.java December 2016
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

package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.ModifierType.PUBLIC;
import static org.snapscript.core.Reserved.TYPE_CLASS;
import static org.snapscript.core.Reserved.TYPE_THIS;

import java.util.List;

import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.property.ConstantPropertyBuilder;
import org.snapscript.core.property.Property;

public class ClassConstantInitializer {
   
   private final ConstantPropertyBuilder builder;
   
   public ClassConstantInitializer() {
      this.builder = new ConstantPropertyBuilder();
   }

   public void declare(Scope scope, Type type) throws Exception {
      declareConstant(scope, TYPE_THIS, type);
      declareConstant(scope, TYPE_CLASS, type, type);
   }
   
   protected void declareConstant(Scope scope, String name, Type type) throws Exception {
      List<Property> properties = type.getProperties();
      Property property = builder.createConstant(name);
      
      properties.add(property);
   }
   
   protected void declareConstant(Scope scope, String name, Type type, Object value) throws Exception {
      List<Property> properties = type.getProperties();
      Property property = builder.createConstant(name, value);
      Value constant = ValueType.getBlank(value, null, PUBLIC.mask | CONSTANT.mask);
      State state = scope.getState();

      properties.add(property);
      state.add(name, constant);
   }
   
   protected void declareConstant(Scope scope, String name, Type type, Type parent, Object value) throws Exception {
      List<Property> properties = type.getProperties();
      Property property = builder.createConstant(name, value, type);
      Value constant = ValueType.getBlank(value, parent, PUBLIC.mask | CONSTANT.mask);
      State state = scope.getState();

      properties.add(property);
      state.add(name, constant);
   }
}
