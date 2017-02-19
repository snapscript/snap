/*
 * ErrorHandler.java December 2016
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

import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.stack.ThreadStack;

public class ErrorHandler {

   private final ExternalErrorHandler external;
   private final InternalErrorHandler internal;
   
   public ErrorHandler(ThreadStack stack) {
      this(stack, true);
   }
   
   public ErrorHandler(ThreadStack stack, boolean replace) {
      this.internal = new InternalErrorHandler(stack, replace);
      this.external = new ExternalErrorHandler();
   }
   
   public Result throwInternal(Scope scope, Object cause) {
      if(InternalError.class.isInstance(cause)) {
         throw (InternalError)cause;
      }
      return internal.throwInternal(scope, cause); // fill in trace
   }
   
   public Result throwExternal(Scope scope, Throwable cause) throws Exception {
      if(InternalError.class.isInstance(cause)) {
         InternalError error = (InternalError)cause;
         Object original = error.getValue();
         
         if(Exception.class.isInstance(original)) {
            throw (Exception)original; // throw original
         }
         return external.throwExternal(scope, original); // no stack trace
      }
      return external.throwExternal(scope, cause); // no stack trace
   }
}
