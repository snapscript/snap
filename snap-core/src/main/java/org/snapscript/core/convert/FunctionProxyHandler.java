/*
 * FunctionProxyHandler.java December 2016
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

import static org.snapscript.core.Reserved.METHOD_EQUALS;
import static org.snapscript.core.Reserved.METHOD_HASH_CODE;
import static org.snapscript.core.Reserved.METHOD_TO_STRING;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Result;
import org.snapscript.core.Transient;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.function.Function;

public class FunctionProxyHandler implements ProxyHandler { 
   
   private final ProxyArgumentExtractor extractor;
   private final Function function;
   private final Context context;
   private final Value value;
   
   public FunctionProxyHandler(ProxyWrapper wrapper, Context context, Function function) {
      this.extractor = new ProxyArgumentExtractor(wrapper);
      this.value = new Transient(function);
      this.function = function;
      this.context = context;
   }
   
   @Override
   public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
      Object[] convert = extractor.extract(arguments);
      String name = method.getName();
      int width = convert.length;
      
      if(name.equals(METHOD_HASH_CODE)) {
         if(width != 0) {
            throw new InternalStateException("Closure '"+ METHOD_HASH_CODE +"' does not accept " + width + " arguments");
         }
         return function.hashCode();
      }
      if(name.equals(METHOD_TO_STRING)) {
         if(width != 0) {
            throw new InternalStateException("Closure '"+METHOD_TO_STRING+"' does not accept " + width + " arguments");
         }
         return function.toString();
      }
      if(name.equals(METHOD_EQUALS)) {
         if(width != 1) {
            throw new InternalStateException("Closure '"+METHOD_EQUALS+"' does not accept " + width + " arguments");
         }
         return function.equals(convert[0]);
      }
      return invoke(convert);
   }
   
   private Object invoke(Object[] arguments) throws Throwable {
      FunctionBinder binder = context.getBinder();  
      Callable<Result> call = binder.bind(value, arguments); // here arguments can be null!!!
      int width = arguments.length;
      
      if(call == null) {
         throw new InternalStateException("Closure not matched with " + width +" arguments");
      }
      Result result = call.call();
      Object data = result.getValue();
      
      return data;  
   }
   
   @Override
   public Function extract() {
      return function;
   }
   
}
