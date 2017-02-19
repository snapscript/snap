/*
 * ModuleDispatcher.java December 2016
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

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.bind.FunctionBinder;

public class ModuleDispatcher implements InvocationDispatcher {
   
   private final ObjectDispatcher handler;
   private final Module module;   
   
   public ModuleDispatcher(Scope scope, Object object) {
      this.handler = new ObjectDispatcher(scope, object);
      this.module = (Module)object;
   }

   @Override
   public Value dispatch(String name, Object... arguments) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();    
      Callable<Result> call = binder.bind(scope, module, name, arguments);
      
      if(call == null) {
         return handler.dispatch(name, arguments);
      }
      Result result = call.call();
      Object data = result.getValue();

      return ValueType.getTransient(data);           
   }
}