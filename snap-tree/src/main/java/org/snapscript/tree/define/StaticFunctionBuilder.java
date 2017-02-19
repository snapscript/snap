/*
 * StaticFunctionBuilder.java December 2016
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

import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.CompoundStatement;

public class StaticFunctionBuilder implements MemberFunctionBuilder {
   
   private final Signature signature;
   private final Statement body;
   private final Type constraint;
   private final String name;
   private final int modifiers;

   public StaticFunctionBuilder(Signature signature, Statement body, Type constraint, String name, int modifiers) {
      this.constraint = constraint;
      this.signature = signature;
      this.modifiers = modifiers;
      this.body = body;
      this.name = name;
   }
   
   @Override
   public Function create(Scope scope, Initializer initializer, Type type){
      Statement initialize = new StaticBody(initializer, type); 
      Statement statement = new CompoundStatement(initialize, body); 
      Invocation invocation = new StaticInvocation(signature, statement, scope, constraint);
      
      return new InvocationFunction(signature, invocation, type, constraint, name, modifiers);
   }
}