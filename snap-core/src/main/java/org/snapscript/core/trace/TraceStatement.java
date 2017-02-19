/*
 * TraceStatement.java December 2016
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

import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.error.ErrorHandler;

public class TraceStatement extends Statement {
   
   private final TraceInterceptor interceptor;
   private final ErrorHandler handler;
   private final Statement statement;
   private final Trace trace;
   
   public TraceStatement(TraceInterceptor interceptor, ErrorHandler handler, Statement statement, Trace trace) {
      this.interceptor = interceptor;
      this.statement = statement;
      this.handler = handler;
      this.trace = trace;
   }
   
   @Override
   public Result compile(Scope scope) throws Exception {
      return statement.compile(scope);
   }
   
   @Override
   public Result execute(Scope scope) throws Exception {
      try {
         interceptor.before(scope, trace);
         return statement.execute(scope); 
      } catch(Exception cause) {
         interceptor.error(scope, trace, cause);
         return handler.throwInternal(scope, cause);
      } finally {
         interceptor.after(scope, trace);
      }
   }
}
