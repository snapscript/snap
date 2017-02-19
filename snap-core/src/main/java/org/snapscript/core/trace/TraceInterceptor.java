/*
 * TraceInterceptor.java December 2016
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

package org.snapscript.core.trace;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.snapscript.core.Scope;
import org.snapscript.core.stack.ThreadStack;

public class TraceInterceptor implements TraceListener {
   
   private final Set<TraceListener> listeners;
   private final ThreadStack stack;
   
   public TraceInterceptor(ThreadStack stack) {
      this.listeners = new CopyOnWriteArraySet<TraceListener>();
      this.stack = stack;
   }
   
   @Override
   public void before(Scope scope, Trace trace) {
      stack.before(trace);
      
      if(!listeners.isEmpty()) {
         for(TraceListener listener : listeners) {
            listener.before(scope, trace);
         }
      }
   }
   
   @Override
   public void error(Scope scope, Trace trace, Exception cause) {
      if(!listeners.isEmpty()) {
         for(TraceListener listener : listeners) {
            listener.error(scope, trace, cause);
         }
      }
   }
   
   @Override
   public void after(Scope scope, Trace trace) {
      stack.after(trace);
      
      if(!listeners.isEmpty()) {
         for(TraceListener listener : listeners) {
            listener.after(scope, trace);
         }
      }
   }

   public void register(TraceListener listener) {
      listeners.add(listener);
   }
   
   public void remove(TraceListener listener) {
      listeners.remove(listener);
   }
}
