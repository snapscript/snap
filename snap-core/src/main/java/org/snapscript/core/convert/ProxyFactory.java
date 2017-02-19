/*
 * ProxyFactory.java December 2016
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

import java.lang.reflect.Proxy;

import org.snapscript.core.Any;
import org.snapscript.core.Context;
import org.snapscript.core.ContextClassLoader;
import org.snapscript.core.Scope;
import org.snapscript.core.function.Function;

public class ProxyFactory {

   private final InterfaceCollector collector;
   private final ProxyWrapper wrapper;
   private final ClassLoader loader;
   private final Context context;
   
   public ProxyFactory(ProxyWrapper wrapper, Context context) {
      this.loader = new ContextClassLoader(Any.class);
      this.collector = new InterfaceCollector();
      this.wrapper = wrapper;
      this.context = context;
   }
   
   public Object create(Scope scope, Class... require) {
      Class[] interfaces = collector.collect(scope);
      
      if(interfaces.length > 0) {
         ScopeProxyHandler handler = new ScopeProxyHandler(wrapper, context, scope);
         TraceProxyHandler tracer = new TraceProxyHandler(handler, context, scope);
         
         return Proxy.newProxyInstance(loader, interfaces, tracer);
      }
      return scope;
   }
   
   public Object create(Function function, Class... require) {
      Class[] interfaces = collector.filter(require);
      
      if(interfaces.length > 0) {
         FunctionProxyHandler handler = new FunctionProxyHandler(wrapper, context, function);

         return Proxy.newProxyInstance(loader, interfaces, handler);
      }
      return function;
   }
}
