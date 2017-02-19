/*
 * SwitchStatement.java December 2016
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

package org.snapscript.tree.condition;

import static org.snapscript.tree.condition.RelationalOperator.EQUALS;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Value;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.core.trace.TraceType;

public class SwitchStatement implements Compilation {
   
   private final Statement statement;
   
   public SwitchStatement(Evaluation evaluation, Case... cases) {
      this.statement = new CompileResult(evaluation, cases);
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

      private final Evaluation condition;
      private final Case[] cases;
      
      public CompileResult(Evaluation condition, Case... cases) {
         this.condition = condition;
         this.cases = cases;
      }
      
      @Override
      public Result compile(Scope scope) throws Exception {
         Result last = ResultType.getNormal();
         
         for(int i = 0; i < cases.length; i++){
            Statement statement = cases[i].getStatement();
            Result result = statement.compile(scope);
            
            if(!result.isNormal()){
               return result;
            }
            last = result;
         }
         return last;
      }
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Value left = condition.evaluate(scope, null);
         
         for(int i = 0; i < cases.length; i++){
            Evaluation evaluation = cases[i].getEvaluation();
            
            if(evaluation == null) {
               Statement statement = cases[i].getStatement();
               Result result = statement.execute(scope);
               
               if(result.isBreak()) {
                  return ResultType.getNormal();
               }
               if(!result.isNormal()) {
                  return result;      
               }
               return ResultType.getNormal();
            }
            Value right = evaluation.evaluate(scope, null);
            Value value = EQUALS.operate(scope, left, right);
            Boolean match = value.getBoolean();
            
            if(match.booleanValue()) {
               for(int j = i; j < cases.length; j++) {
                  Statement statement = cases[j].getStatement();
                  Result result = statement.execute(scope);
   
                  if(result.isBreak()) {
                     return ResultType.getNormal();
                  }
                  if(!result.isNormal()) {
                     return result;      
                  }
               }   
               return ResultType.getNormal();
            }  
         }
         return ResultType.getNormal();
      }
   }
}
