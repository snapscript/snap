/*
 * ThreadBuilder.java December 2016
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

package org.snapscript.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadBuilder implements ThreadFactory {   
   
   private static final String THREAD_TEMPLATE = "%s: Thread-%s";
   private static final String THREAD_DEFAULT = "Thread";
   
   private final ThreadNameBuilder builder;
   private final boolean daemon;   
   private final int stack;
   
   public ThreadBuilder() {
      this(true);
   }   

   public ThreadBuilder(boolean daemon) {
      this(daemon, 0);
   }
   
   public ThreadBuilder(boolean daemon, int stack) {
      this.builder = new ThreadNameBuilder();
      this.daemon = daemon;
      this.stack = stack;
   }
   
   @Override
   public Thread newThread(Runnable task) {
      Thread thread = new Thread(null, task, THREAD_DEFAULT, stack);
      
      if(task != null) {
         String name = builder.createName(task);
         
         thread.setDaemon(daemon);
         thread.setName(name);
      }
      return thread;
   }

   public Thread newThread(Runnable task, Class type) {
      Thread thread = new Thread(null, task, THREAD_DEFAULT, stack);
      
      if(task != null) {
         String name = builder.createName(type);
         
         thread.setDaemon(daemon);
         thread.setName(name);
      }
      return thread;
   }
   
   private class ThreadNameBuilder {
      
      private final AtomicInteger counter;
      
      public ThreadNameBuilder() {
         this.counter = new AtomicInteger(1);
      }
   
      private String createName(Runnable task) {
         Class type = task.getClass();
         String prefix = type.getSimpleName();      
         int count = counter.getAndIncrement();
   
         return String.format(THREAD_TEMPLATE, prefix, count);
      }
      
      private String createName(Class type) {
         String prefix = type.getSimpleName();
         int count = counter.getAndIncrement();
   
         return String.format(THREAD_TEMPLATE, prefix, count);
      }
   }
}
