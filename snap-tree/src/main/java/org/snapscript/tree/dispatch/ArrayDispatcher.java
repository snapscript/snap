/*
 * ArrayDispatcher.java December 2016
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

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.tree.collection.ArrayBuilder;

public class ArrayDispatcher implements InvocationDispatcher {
   
   private final ArrayBuilder builder;
   private final Object object;
   private final Scope scope;      
   
   public ArrayDispatcher(Scope scope, Object object) {
      this.builder = new ArrayBuilder();
      this.object = object;
      this.scope = scope;
   }

   @Override
   public Value dispatch(String name, Object... arguments) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      List list = builder.convert(object);
      Callable<Result> call = binder.bind(scope, list, name, arguments);
      
      if(call == null) {
         TypeExtractor extractor = context.getExtractor();
         Type type = extractor.getType(object);        
         
         throw new InternalStateException("Method '" + name + "' not found for '" + type + "[]'");
      }
      Result result = call.call();
      Object value = result.getValue();

      return ValueType.getTransient(value);
   }
}