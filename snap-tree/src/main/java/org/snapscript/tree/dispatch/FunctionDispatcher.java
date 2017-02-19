/*
 * FunctionDispatcher.java December 2016
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

package org.snapscript.tree.dispatch;

import java.util.List;
import java.util.concurrent.Callable;

import org.snapscript.core.Any;
import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.Signature;

public class FunctionDispatcher implements InvocationDispatcher {
   
   private final ObjectDispatcher dispatcher;
   private final FunctionAdapter adapter;
   private final Object function;
   private final Scope scope;      
   
   public FunctionDispatcher(Scope scope, Object function) {
      this.adapter = new FunctionAdapter(function);
      this.dispatcher = new ObjectDispatcher(scope, adapter);
      this.function = function;
      this.scope = scope;
   }

   @Override
   public Value dispatch(String name, Object... arguments) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      Callable<Result> call = binder.bind(scope, function, name, arguments); // this is not used often
      
      if(call == null) {
         return dispatcher.dispatch(name, arguments);
      }
      Result result = call.call();
      Object value = result.getValue();

      return ValueType.getTransient(value);
   }
   
   private static class FunctionAdapter implements Any {
      
      private final Function function;
      
      public FunctionAdapter(Object function) {
         this.function = (Function)function;
      }
      
      public int getModifiers() {
         return function.getModifiers();
      }
      
      public Type getType() {
         return function.getType();
      }
      
      public Type getHandle() {
         return function.getHandle();
      }
      
      public Type getConstraint() {
         return function.getConstraint();
      }
      
      public String getName() {
         return function.getName();
      }
      
      public Signature getSignature() {
         return function.getSignature();
      }
      
      public List<Annotation> getAnnotations() {
         return function.getAnnotations();
      }
      
      public Invocation getInvocation() {
         return function.getInvocation();
      }
      
      public String getDescription() {
         return function.getDescription();
      }
      
      @Override
      public String toString() {
         return function.toString();
      }
   }
}