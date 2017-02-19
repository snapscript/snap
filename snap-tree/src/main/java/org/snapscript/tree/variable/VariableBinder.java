/*
 * VariableBinder.java December 2016
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

import java.util.Collection;
import java.util.Map;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Instance;

public class VariableBinder {
   
   public VariableBinder() {
      super();
   }
   
   public ValueResolver bind(Scope scope, Object left, String name) {
      if(left != null) {
         Class type = left.getClass();
         
         if(Module.class.isInstance(left)) {
            return new ModuleResolver(name);
         }
         if(Map.class.isInstance(left)) {
            return new MapResolver(name);
         }         
         if(Scope.class.isInstance(left)) {
            return new ScopeResolver(name);
         }
         if(Type.class.isInstance(left)) {
            return new TypeResolver(name);
         }
         if(Collection.class.isInstance(left)) {
            return new CollectionResolver(name);
         }
         if(type.isArray()) {
            return new ArrayResolver(name);
         }
         return new ObjectResolver(name);
      }
      if(Instance.class.isInstance(scope)) {
         return new InstanceResolver(name);
      }
      return new LocalResolver(name);
   }
}
