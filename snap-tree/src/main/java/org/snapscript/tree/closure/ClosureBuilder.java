/*
 * ClosureBuilder.java December 2016
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

package org.snapscript.tree.closure;

import static org.snapscript.core.Reserved.METHOD_CLOSURE;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionType;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;

public class ClosureBuilder {

   private final Statement statement;
   private final Module module;
   
   public ClosureBuilder(Statement statement, Module module) {
      this.statement = statement;
      this.module = module;
   }

   public Function create(Signature signature, Scope scope) {
      return create(signature, scope, 0);
   }
   
   public Function create(Signature signature, Scope scope, int modifiers) {
      Type type = new FunctionType(signature, module);
      Invocation invocation = new ClosureInvocation(signature, statement, scope);
      
      return new InvocationFunction(signature, invocation, type, null, METHOD_CLOSURE, modifiers);
   }
}