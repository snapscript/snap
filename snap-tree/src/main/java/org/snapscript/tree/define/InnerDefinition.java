/*
 * InnerDefinition.java December 2016
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

import java.util.List;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.property.ConstantPropertyBuilder;
import org.snapscript.core.property.Property;

public class InnerDefinition implements TypePart {
   
   private final ConstantPropertyBuilder builder;
   private final Statement statement;
   
   public InnerDefinition(Statement statement) {
      this.builder = new ConstantPropertyBuilder();
      this.statement = statement;
   }

   @Override
   public Initializer define(Initializer initializer, Type outer) throws Exception {
      Scope scope = outer.getScope();
      statement.define(scope);
      return null;
   }

   @Override
   public Initializer compile(Initializer initializer, Type outer) throws Exception {
      List<Property> properties = outer.getProperties();
      Scope scope = outer.getScope();
      Result result = statement.compile(scope);
      Type inner = result.getValue();
      
      if(inner != null) {
         String name = inner.getName();
         String prefix = outer.getName();
         String key = name.replace(prefix+'$', ""); // get the class name
         Property property = builder.createConstant(key, inner, inner);
         
         properties.add(property);
      }
      return null;
   }
}
