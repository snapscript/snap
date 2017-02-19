/*
 * TryStatement.java December 2016
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

import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.convert.CompatibilityChecker;
import org.snapscript.core.error.ErrorCauseExtractor;
import org.snapscript.core.function.Parameter;
import org.snapscript.tree.function.ParameterDeclaration;

public class TryStatement extends Statement {

   private final Statement statement;
   private final Statement finish;
   private final CatchBlockList list;
   
   public TryStatement(Statement statement, Statement finish) {
      this(statement, null, finish);
   }
   
   public TryStatement(Statement statement, CatchBlockList list) {
      this(statement, list, null);
   }
   
   public TryStatement(Statement statement, CatchBlockList list, Statement finish) {
      this.statement = statement;
      this.finish = finish;  
      this.list = list;
   }    
   
   @Override
   public Result compile(Scope scope) throws Exception {  
      if(list != null) {
         list.compile(scope);
      }
      if(finish != null) {
         finish.compile(scope);
      }
      return statement.compile(scope);
   }

   @Override
   public Result execute(Scope scope) throws Exception {
      Result result = handle(scope);
      
      try {
         if(list != null) {
            if(result.isThrow()) {
               return list.execute(scope, result);            
            }   
         }
      } finally {
         if(finish != null) {
            finish.execute(scope); // what about an exception here
         }
      }
      return result;
   }
   
   private Result handle(Scope scope) throws Exception {
      try {
         return statement.execute(scope);
      } catch(Throwable cause) {
         return ResultType.getThrow(cause);
      }
   }
}
