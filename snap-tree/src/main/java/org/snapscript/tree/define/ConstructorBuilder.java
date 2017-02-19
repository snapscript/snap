/*
 * ConstructorBuilder.java December 2016
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

import static org.snapscript.core.ModifierType.STATIC;
import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.function.StatementInvocation;

public class ConstructorBuilder {
   
   private final Initializer delegate;
   private final Statement statement;
   private final Signature signature;

   public ConstructorBuilder(Signature signature, Statement statement, Initializer delegate) {
      this.signature = signature;
      this.statement = statement;
      this.delegate = delegate;
   }
   
   public Function create(Initializer initializer, Type type, int modifiers) {
      return create(initializer, type, modifiers);
   }
   
   public Function create(Initializer initializer, Type type, int modifiers, boolean compile) {
      Invocation body = new StatementInvocation(signature, statement, null);
      Allocator instance = new InstanceAllocator(initializer, body, type);
      Allocator base = new SuperAllocator(signature, delegate, instance); 
      Invocation constructor = new NewInvocation(initializer, base, type, compile);
      
      return new InvocationFunction(signature, constructor, type, type, TYPE_CONSTRUCTOR, modifiers | STATIC.mask, 1);
   }
}