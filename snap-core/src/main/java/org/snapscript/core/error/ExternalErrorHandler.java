/*
 * ExternalErrorHandler.java December 2016
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

import org.snapscript.core.InternalException;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;

public class ExternalErrorHandler {

   public ExternalErrorHandler() {
      super();
   }

   public Result throwExternal(Scope scope, Object cause) throws Exception {
      if(Exception.class.isInstance(cause)) {
         throw (Exception)cause;
      }
      if(Throwable.class.isInstance(cause)) {
         throw new InternalException((Throwable)cause);
      }
      throw new InternalException(String.valueOf(cause));
   }
}
