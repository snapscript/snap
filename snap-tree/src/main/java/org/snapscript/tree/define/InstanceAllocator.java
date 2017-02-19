/*
 * InstanceAllocator.java December 2016
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

import static org.snapscript.core.Reserved.TYPE_THIS;

import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.define.Instance;
import org.snapscript.core.function.Invocation;

public class InstanceAllocator implements Allocator {
   
   private final ObjectInstanceBuilder builder;
   private final Initializer initializer;
   private final Invocation invocation;
   
   public InstanceAllocator(Initializer initializer, Invocation invocation, Type type) {
      this.builder = new ObjectInstanceBuilder(type);
      this.initializer = initializer;
      this.invocation = invocation;
   }
   
   @Override
   public Instance allocate(Scope scope, Instance base, Object... list) throws Exception {
      Type real = (Type)list[0];
      Instance instance = builder.create(scope, base, real); // we need to pass the base type up!
      State state = instance.getState();
      Value value = state.get(TYPE_THIS);
      
      if(instance != base) { // false if this(...) is called
         value.setValue(instance); // set the 'this' variable
         initializer.execute(instance, real);
         invocation.invoke(instance, instance, list);
         
         return instance;
      }
      invocation.invoke(instance, instance, list);
      
      return instance;    
   }
}
