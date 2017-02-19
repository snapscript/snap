/*
 * ModuleBody.java December 2016
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

package org.snapscript.tree.define;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class ModuleBody extends Statement {

   private final Statement[] statements;
   private final AtomicBoolean execute;
   private final AtomicBoolean compile;
   private final AtomicBoolean define;
   
   public ModuleBody(Statement... statements) {
      this.execute = new AtomicBoolean(true);
      this.compile = new AtomicBoolean(true);
      this.define = new AtomicBoolean(true);
      this.statements = statements;
   }
   
   @Override
   public Result define(Scope scope) throws Exception {
      Result last = ResultType.getNormal();
      
      if(define.compareAndSet(true, false)) {
         for(Statement statement : statements) {
            Result result = statement.define(scope);
            
            if(!result.isNormal()){
               return result;
            }
            last = result;
         }
      }
      return last;
   }
   
   @Override
   public Result compile(Scope scope) throws Exception {
      Result last = ResultType.getNormal();
      
      if(compile.compareAndSet(true, false)) {
         for(Statement statement : statements) {
            Result result = statement.compile(scope);
            
            if(!result.isNormal()){
               return result;
            }
            last = result;
         }
      }
      return last;
   }
   
   @Override
   public Result execute(Scope scope) throws Exception {
      Result last = ResultType.getNormal();
      
      if(execute.compareAndSet(true, false)) {
         for(Statement statement : statements) {
            Result result = statement.execute(scope);
            
            if(!result.isNormal()){
               return result;
            }
            last = result;
         }
      }
      return last;
   }
}
