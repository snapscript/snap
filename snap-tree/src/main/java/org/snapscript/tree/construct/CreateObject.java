/*
 * CreateObject.java December 2016
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

package org.snapscript.tree.construct;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.tree.ArgumentList;

public class CreateObject implements Evaluation {
   
   private final ArgumentList arguments;
   private final Evaluation reference;

   public CreateObject(Evaluation reference, ArgumentList arguments) {
      this.reference = reference;
      this.arguments = arguments;
   }      
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception { // this is rubbish
      Value value = reference.evaluate(scope, null);
      Type type = value.getValue();
      Callable<Result> call = bind(scope, type);
           
      if(call == null){
         throw new InternalStateException("No constructor for '" + type + "'");
      }
      Result result = call.call();
      Object instance = result.getValue();
      
      return ValueType.getTransient(instance);
   }
   
   private Callable<Result> bind(Scope scope, Type type) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      Class real = type.getType();
      
      if(arguments != null) {
         if(real == null) {
            Value array = arguments.create(scope, type); 
            Object[] arguments = array.getValue();
            
            return binder.bind(scope, type, TYPE_CONSTRUCTOR, arguments);
         }
         Value array = arguments.create(scope); 
         Object[] arguments = array.getValue();
         
         return binder.bind(scope, type, TYPE_CONSTRUCTOR, arguments);
      }
      if(real == null) {
         return binder.bind(scope, type, TYPE_CONSTRUCTOR, type);
      }
      return binder.bind(scope, type, TYPE_CONSTRUCTOR);
   }
}
