/*
 * PackageDefinitionList.java December 2016
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

package org.snapscript.core.link;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Path;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class PackageDefinitionList implements PackageDefinition {
   
   private final List<PackageDefinition> definitions;
   
   public PackageDefinitionList(List<PackageDefinition> definitions) {
      this.definitions = definitions;
   }

   @Override
   public Statement compile(Scope scope, Path from) throws Exception {
      List<Statement> statements = new ArrayList<Statement>();
      
      for(PackageDefinition definition : definitions) {
         Statement statement = definition.compile(scope, from);
         
         if(statement != null) {
            statements.add(statement);
         }
      }
      return new StatementList(statements);
   }
   
   private class StatementList extends Statement {
      
      private final List<Statement> statements;
      
      public StatementList(List<Statement> statements) {
         this.statements = statements;
      }
      
      public Result define(Scope scope) throws Exception {
         Result last = ResultType.getNormal();
         
         for(Statement statement : statements){
            Result result = statement.define(scope);
         
            if(!last.isDeclare()) {
               last = result;
            }
            last = result;
         }
         return last;
      }
                     
      public Result compile(Scope scope) throws Exception {
         Result last = ResultType.getNormal();
         
         for(Statement statement : statements){
            Result result = statement.compile(scope);
         
            if(!last.isDeclare()) {
               last = result;
            }
            last = result;
         }
         return last;
      }
      
      public Result execute(Scope scope) throws Exception {
         Result result = ResultType.getNormal();
         
         for(Statement statement : statements){
            Result next = statement.execute(scope);
         
            if(!next.isNormal()){
               return next;
            }
            result = next;
         }
         return result;
      }
   }

}
