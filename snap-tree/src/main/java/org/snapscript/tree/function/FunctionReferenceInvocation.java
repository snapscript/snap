/*
 * FunctionReferenceInvocation.java December 2016
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

package org.snapscript.tree.function;

import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.function.Invocation;
import org.snapscript.tree.dispatch.InvocationBinder;
import org.snapscript.tree.dispatch.InvocationDispatcher;

public class FunctionReferenceInvocation implements Invocation {

   private final FunctionReferenceAligner aligner;
   private final InvocationBinder binder;
   private final Module module;
   private final String method;
   private final Object value;
   
   public FunctionReferenceInvocation(Module module, Object value, String method) {
      this.aligner = new FunctionReferenceAligner(value, method);
      this.binder = new InvocationBinder();
      this.module = module;
      this.method = method;
      this.value = value;
   }

   @Override
   public Result invoke(Scope scope, Object object, Object... list) throws Exception {
      Scope actual = module.getScope();
      Object[] arguments = aligner.align(list); // align constructor arguments
      InvocationDispatcher dispatcher = binder.bind(actual, value);
      Value value = dispatcher.dispatch(method, arguments);
      Object result = value.getValue();
    
      return ResultType.getNormal(result);
   }
}
