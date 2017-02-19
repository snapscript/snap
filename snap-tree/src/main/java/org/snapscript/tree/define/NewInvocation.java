/*
 * NewInvocation.java December 2016
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

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.define.Instance;
import org.snapscript.core.function.Invocation;

public class NewInvocation implements Invocation<Instance>{
   
   private final StaticInstanceBuilder builder;
   private final Initializer initializer;
   private final AtomicBoolean compile;
   private final Allocator allocator;
   private final Type type;
   
   public NewInvocation(Initializer initializer, Allocator allocator, Type type) {
      this(initializer, allocator, type, true);
   }
   
   public NewInvocation(Initializer initializer, Allocator allocator, Type type, boolean compile) {
      this.builder = new StaticInstanceBuilder(type);
      this.compile = new AtomicBoolean(compile);
      this.initializer = initializer;
      this.allocator = allocator;
      this.type = type;
   }

   @Override
   public Result invoke(Scope scope, Instance base, Object... list) throws Exception {
      Type real = (Type)list[0];
      Instance inner = builder.create(scope, base, real);

      if(compile.compareAndSet(true, false)) {
         initializer.compile(scope, type); // static stuff if needed
      }
      Instance result = allocator.allocate(scope, inner, list);
      return ResultType.getNormal(result);
   }
}
