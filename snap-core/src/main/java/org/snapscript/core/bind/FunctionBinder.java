/*
 * FunctionBinder.java December 2016
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

package org.snapscript.core.bind;

import java.util.concurrent.Callable;

import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.Value;
import org.snapscript.core.stack.ThreadStack;

public class FunctionBinder {
   
   private final ObjectFunctionMatcher objects;
   private final ModuleFunctionMatcher modules;
   private final ValueFunctionMatcher values;
   private final ScopeFunctionMatcher scopes;
   private final TypeFunctionMatcher types;
   
   public FunctionBinder(TypeExtractor extractor, ThreadStack stack) {
      this.objects = new ObjectFunctionMatcher(extractor, stack);
      this.modules = new ModuleFunctionMatcher(extractor, stack);
      this.types = new TypeFunctionMatcher(extractor, stack);
      this.values = new ValueFunctionMatcher(stack);
      this.scopes = new ScopeFunctionMatcher(stack);
   }
   
   public Callable<Result> bind(Value value, Object... list) throws Exception { // closures
      FunctionPointer call = values.match(value, list);
      
      if(call != null) {
         return new FunctionCall(call, null, null);
      }
      return null;
   }
   
   public Callable<Result> bind(Scope scope, String name, Object... list) throws Exception { // function variable
      FunctionPointer call = scopes.match(scope, name, list);
      
      if(call != null) {
         return new FunctionCall(call, scope, scope);
      }
      return null;
   }
   
   public Callable<Result> bind(Scope scope, Module module, String name, Object... list) throws Exception {
      FunctionPointer call = modules.match(module, name, list);
      
      if(call != null) {
         return new FunctionCall(call, scope, module);
      }
      return null;
   }
   
   public Callable<Result> bind(Scope scope, Type type, String name, Object... list) throws Exception {
      FunctionPointer call = types.match(type, name, list);
      
      if(call != null) {
         return new FunctionCall(call, scope, null);
      }
      return null;
   }

   public Callable<Result> bind(Scope scope, Object source, String name, Object... list) throws Exception {
      FunctionPointer call = objects.match(source, name, list);
      
      if(call != null) {
         return new FunctionCall(call, scope, source);
      }
      return null;
   }
}
