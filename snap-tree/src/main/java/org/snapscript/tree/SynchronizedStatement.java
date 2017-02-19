/*
 * SynchronizedStatement.java December 2016
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

package org.snapscript.tree;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Value;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.core.trace.TraceType;

public class SynchronizedStatement implements Compilation {
   
   private final Statement statement;
   
   public SynchronizedStatement(Evaluation evaluation, Statement statement) {
      this.statement = new CompileResult(evaluation, statement);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = TraceType.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, statement, trace);
   }
   
   private static class CompileResult extends Statement {

      private final Evaluation reference;
      private final Statement statement;
      
      public CompileResult(Evaluation reference, Statement statement) {
         this.reference = reference;
         this.statement = statement;
      }
      
      @Override
      public Result compile(Scope scope) throws Exception {
         return statement.compile(scope);
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Value value = reference.evaluate(scope, null);
         Object object = value.getValue();
         
         synchronized(object) {
            return statement.execute(scope);
         }
      }
   }
}
