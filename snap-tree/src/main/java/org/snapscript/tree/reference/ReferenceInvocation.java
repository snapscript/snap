/*
 * ReferenceInvocation.java December 2016
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

package org.snapscript.tree.reference;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.tree.ArgumentList;

public class ReferenceInvocation implements Evaluation {
   
   private final ArgumentList arguments;
   
   public ReferenceInvocation(ArgumentList arguments) {
      this.arguments = arguments;
   }
      
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception { 
      Module module = scope.getModule();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      Value value = ValueType.getTransient(left);        
      Value array = arguments.create(scope); 
      Object[] arguments = array.getValue();
      Callable<Result> call = binder.bind(value, arguments);
      int width = arguments.length;
      
      if(call == null) {
         throw new InternalStateException("Result was not a closure of " + width +" arguments");
      }
      Result result = call.call();
      Object object = result.getValue();
      
      return ValueType.getTransient(object);
   }
}