/*
 * ScopeProxyHandler.java December 2016
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

package org.snapscript.core.convert;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.bind.FunctionBinder;

public class ScopeProxyHandler implements ProxyHandler {
   
   private final ProxyArgumentExtractor extractor;
   private final Context context;
   private final Scope scope;
   
   public ScopeProxyHandler(ProxyWrapper wrapper, Context context, Scope scope) {
      this.extractor = new ProxyArgumentExtractor(wrapper);
      this.context = context;
      this.scope = scope;
   }
   
   @Override
   public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
      String name = method.getName();
      FunctionBinder binder = context.getBinder();  
      Object[] convert = extractor.extract(arguments);
      Callable<Result> call = binder.bind(scope, scope, name, convert); // here arguments can be null!!!
      
      if(call == null) {
         throw new InternalStateException("Method '" + name + "' not found");
      }
      Result result = call.call();
      Object data = result.getValue();
      
      return data;   
   }
   
   @Override
   public Scope extract() {
      return scope;
   }   
}
