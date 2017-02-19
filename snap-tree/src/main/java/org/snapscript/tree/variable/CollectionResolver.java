/*
 * CollectionResolver.java December 2016
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

package org.snapscript.tree.variable;

import static org.snapscript.core.Reserved.PROPERTY_LENGTH;

import java.util.Collection;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;

public class CollectionResolver implements ValueResolver<Collection> {
   
   private final ObjectResolver resolver;
   private final String name;
   
   public CollectionResolver(String name) {
      this.resolver = new ObjectResolver(name);
      this.name = name;
   }
   
   @Override
   public Value resolve(Scope scope, Collection left) {
      if(name.equals(PROPERTY_LENGTH)) {
         int length = left.size();
         return ValueType.getConstant(length);
      }
      return resolver.resolve(scope, left);
   }
}