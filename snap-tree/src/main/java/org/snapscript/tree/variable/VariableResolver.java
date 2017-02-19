/*
 * VariableResolver.java December 2016
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

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueKeyBuilder;

public class VariableResolver {
   
   private final Cache<Object, ValueResolver> resolvers;
   private final ValueKeyBuilder builder;
   private final VariableBinder binder;
   
   public VariableResolver() {
      this.resolvers = new CopyOnWriteCache<Object, ValueResolver>();
      this.builder = new ValueKeyBuilder();
      this.binder = new VariableBinder();
   }
   
   public Value resolve(Scope scope, Object left, String name) throws Exception {
      Object key = builder.create(scope, left, name);
      ValueResolver resolver = resolvers.fetch(key);
      
      if(resolver == null) { 
         resolver = binder.bind(scope, left, name);
         resolvers.cache(key, resolver);
      }
      return resolver.resolve(scope, left);
   }
}
