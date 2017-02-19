/*
 * ThreadStack.java December 2016
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

package org.snapscript.core.stack;

import org.snapscript.common.Stack;
import org.snapscript.core.function.Function;

public class ThreadStack {
   
   private final StackTraceBuilder builder;
   private final ThreadLocalStack local;
   
   public ThreadStack() {
      this.builder = new StackTraceBuilder();
      this.local = new ThreadLocalStack();
   }
   
   public Function current() {
      Stack stack = local.get();
      
      for(Object entry : stack) {
         if(Function.class.isInstance(entry)) {
            return (Function)entry;
         }
      }
      return null;
   }
   
   public StackTraceElement[] build() {
      return build(null);
   }
   
   public StackTraceElement[] build(Throwable cause) {
      Stack stack = local.get();
      
      if(cause != null) {
         return builder.create(stack, cause);   
      }
      return builder.create(stack);
   }
   
   public void before(Object trace) {
      Stack stack = local.get();
      
      if(trace != null) {
         stack.push(trace);
      }
   }
   
   public void after(Object trace) { // remove from stack
      Stack stack = local.get();
      
      while(!stack.isEmpty()) {
         Object next = stack.pop();
         
         if(next == trace) {
            break;
         }
      }
   }
   
   public void clear() {
      Stack stack = local.get();
      stack.clear();
   }

}
