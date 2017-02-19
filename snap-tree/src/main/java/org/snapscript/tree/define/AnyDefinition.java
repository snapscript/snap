/*
 * AnyDefinition.java December 2016
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

package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.ModifierType.PUBLIC;
import static org.snapscript.core.Reserved.ANY_TYPE;
import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;
import static org.snapscript.core.Reserved.METHOD_EQUALS;
import static org.snapscript.core.Reserved.METHOD_HASH_CODE;
import static org.snapscript.core.Reserved.METHOD_NOTIFY;
import static org.snapscript.core.Reserved.METHOD_NOTIFY_ALL;
import static org.snapscript.core.Reserved.METHOD_TO_STRING;
import static org.snapscript.core.Reserved.METHOD_WAIT;
import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;
import static org.snapscript.core.Reserved.TYPE_THIS;

import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.define.Instance;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;

public class AnyDefinition{
   
   private final AnyFunctionBuilder builder;
   
   public AnyDefinition(){
      this.builder = new AnyFunctionBuilder();
   }

   public Type create(Scope scope) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      TypeLoader loader = context.getLoader();
      Type result = loader.defineType(DEFAULT_PACKAGE, ANY_TYPE);
      List<Function> functions = result.getFunctions();
      
      if(functions.isEmpty()) { // thread safe?
         Function constructor = builder.create(result, TYPE_CONSTRUCTOR, NewInvocation.class, Type.class);
         Function hashCode = builder.create(result, METHOD_HASH_CODE, HashCodeInvocation.class);
         Function toString = builder.create(result, METHOD_TO_STRING, ToStringInvocation.class);
         Function equals = builder.create(result, METHOD_EQUALS, EqualsInvocation.class, Object.class);
         Function wait = builder.create(result, METHOD_WAIT, WaitInvocation.class);
         Function waitFor = builder.create(result, METHOD_WAIT, WaitForInvocation.class, Long.class);
         Function notify = builder.create(result, METHOD_NOTIFY, NotifyInvocation.class);
         Function notifyAll = builder.create(result, METHOD_NOTIFY_ALL, NotifyAllInvocation.class);
         
         functions.add(constructor);
         functions.add(wait);
         functions.add(waitFor);
         functions.add(notify);
         functions.add(notifyAll);
         functions.add(hashCode);
         functions.add(equals);
         functions.add(toString);
      }
      return result;
   }
   
   private static class NewInvocation implements Invocation<Object> {
      
      private final AnyInstanceBuilder builder;
      
      public NewInvocation() {
         this.builder = new AnyInstanceBuilder();
      }
      
      @Override
      public Result invoke(Scope scope, Object object, Object... list) throws Exception {
         Type real = (Type)list[0];
         Instance instance = builder.create(scope, real);
         State state = instance.getState();
         Value value = ValueType.getProperty(object, real, PUBLIC.mask | CONSTANT.mask); // this needs to be a blank
         
         state.add(TYPE_THIS, value); // reference to 'this'
         
         return ResultType.getNormal(instance);
      }
   }
   
   private static class WaitInvocation implements Invocation<Object> {
      
      public WaitInvocation() {
         super();
      }
      
      @Override
      public Result invoke(Scope scope, Object object, Object... list) throws Exception {
         object.wait();
         return ResultType.getNormal();
      }
   }
   
   private static class WaitForInvocation implements Invocation<Object> {
      
      public WaitForInvocation() {
         super();
      }
      
      @Override
      public Result invoke(Scope scope, Object object, Object... list) throws Exception {
         Number argument = (Number)list[0];
         long time = argument.longValue();
         
         object.wait(time);
         return ResultType.getNormal();
      }
   }
   
   private static class NotifyInvocation implements Invocation<Object> {
      
      public NotifyInvocation() {
         super();
      }
      
      @Override
      public Result invoke(Scope scope, Object object, Object... list) throws Exception {
         object.notify();
         return ResultType.getNormal();
      }
   }
   
   private static class NotifyAllInvocation implements Invocation<Object> {
      
      public NotifyAllInvocation() {
         super();
      }
      
      @Override
      public Result invoke(Scope scope, Object object, Object... list) throws Exception {
         object.notifyAll();
         return ResultType.getNormal();
      }
   }
   
   private static class HashCodeInvocation implements Invocation<Object> {
      
      public HashCodeInvocation() {
         super();
      }

      @Override
      public Result invoke(Scope scope, Object object, Object... list) throws Exception {
         int hash = object.hashCode();
         return ResultType.getNormal(hash);
      }
   }
   
   private static class EqualsInvocation implements Invocation<Object> {
      
      public EqualsInvocation() {
         super();
      }

      @Override
      public Result invoke(Scope scope, Object object, Object... list) throws Exception {
         Object argument = list[0];
         boolean equal = object.equals(argument);
         
         return ResultType.getNormal(equal);
      }
   }
   
   private static class ToStringInvocation implements Invocation<Object> {
      
      public ToStringInvocation() {
         super();
      }
      
      @Override
      public Result invoke(Scope scope, Object object, Object... list) throws Exception {
         String value = object.toString();
         int hash = object.hashCode();
         
         return ResultType.getNormal(value + "@" + hash);
      }
   }
}
