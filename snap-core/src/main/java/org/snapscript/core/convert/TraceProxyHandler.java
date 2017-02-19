/*
 * TraceProxyHandler.java December 2016
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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceType;

public class TraceProxyHandler implements ProxyHandler {
   
   private final InvocationHandler delegate;
   private final Context context;
   private final Scope scope;

   public TraceProxyHandler(InvocationHandler delegate, Context context, Scope scope) {
      this.context = context;
      this.delegate = delegate;
      this.scope = scope;
   }

   @Override
   public Object invoke(Object proxy, Method method, Object[] list) throws Throwable {
      Module module = scope.getModule();
      Path path = module.getPath();
      Trace trace = TraceType.getNative(module, path); 
      TraceInterceptor interceptor = context.getInterceptor();
      ErrorHandler handler = context.getHandler();
      
      try {
         interceptor.before(scope, trace);
         return delegate.invoke(proxy, method, list); 
      } catch(Exception cause) {
         return handler.throwInternal(scope, cause);
      } finally {
         interceptor.after(scope, trace);
      }
   }
   
   @Override
   public Scope extract() {
      return scope;
   }  
}
