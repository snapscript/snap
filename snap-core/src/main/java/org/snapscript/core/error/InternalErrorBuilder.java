/*
 * InternalErrorBuilder.java December 2016
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

package org.snapscript.core.error;

import org.snapscript.core.stack.ThreadStack;

public class InternalErrorBuilder {

   private final ThreadStack stack;
   private final boolean replace;
   
   public InternalErrorBuilder(ThreadStack stack) {
      this(stack, true);
   }
   
   public InternalErrorBuilder(ThreadStack stack, boolean replace) {
      this.replace = replace;
      this.stack = stack;
   }
   
   public InternalError create(Object value) {
      InternalError error = new InternalError(value);
      
      if(replace) {
         if(Throwable.class.isInstance(value)) {
            Throwable cause = (Throwable)value;
            StackTraceElement[] trace = stack.build(cause);
            
            if(trace.length > 0) {
               cause.setStackTrace(trace);
               error.setStackTrace(trace);
            }
         } else {
            StackTraceElement[] trace = stack.build();
            
            if(trace.length > 0) {
               error.setStackTrace(trace); // when there is no cause
            }
         }
      }
      return error;
   }
}
