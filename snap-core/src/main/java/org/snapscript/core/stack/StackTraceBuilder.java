/*
 * StackTraceBuilder.java December 2016
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

import static org.snapscript.core.Reserved.IMPORT_SNAPSCRIPT;

import java.util.List;

import org.snapscript.common.Stack;

public class StackTraceBuilder {
   
   private final OriginTraceExtractor extractor;
   private final StackElementConverter builder;
   private final StackTraceElement[] empty;
   
   public StackTraceBuilder() {
      this.extractor = new OriginTraceExtractor();
      this.builder = new StackElementConverter();
      this.empty = new StackTraceElement[]{};
   }
   
   public StackTraceElement[] create(Stack stack) {
      return create(stack, null);
   }
   
   public StackTraceElement[] create(Stack stack, Throwable origin) {
      Thread thread = Thread.currentThread();
      List<StackTraceElement> list = extractor.extract(origin); // debug cause
      List<StackTraceElement> context = builder.create(stack); // script stack
      StackTraceElement[] actual = thread.getStackTrace(); // native stack
      
      for(StackTraceElement trace : context) {
         list.add(trace);
      }
      for(int i = 1; i < actual.length; i++) { // strip Thread.getStackTrace
         StackTraceElement trace = actual[i];
         String source = trace.getClassName();
         
         if(!source.startsWith(IMPORT_SNAPSCRIPT)) { // not really correct, stripping required elements!
            list.add(trace);
         }
      } 
      return list.toArray(empty);
   }
}
