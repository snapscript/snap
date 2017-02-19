/*
 * StatementDefinition.java December 2016
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

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.NoStatement;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;

public class StatementDefinition implements PackageDefinition {

   private final AtomicReference<Statement> reference;
   private final Statement statement;
   private final Statement empty;
   private final String name;
   private final Path path;
   
   public StatementDefinition(Statement statement, Path path, String name) {
      this.reference = new AtomicReference<Statement>();
      this.empty = new NoStatement();
      this.statement = statement;
      this.name = name;
      this.path = path;
   }

   @Override
   public Statement compile(Scope scope, Path from) throws Exception {
      if(!path.equals(from)) { // don't import yourself
         Statement value = reference.get();
         
         if(value == null) {
            Executable executable = new Executable(scope);
            FutureTask<Statement> task = new FutureTask<Statement>(executable);
            FutureStatement result = new FutureStatement(task, path);
            
            if(reference.compareAndSet(null, result)) {
               task.run();
               return result;
            }
         }
         return reference.get(); // return future package
      }
      return empty;
   }
   
   private class Executable implements Callable<Statement> {
      
      private final Scope scope;
      
      public Executable(Scope scope) {
         this.scope = scope;
      }

      @Override
      public Statement call() throws Exception {
         try {
            Module module = scope.getModule();
            Context context = module.getContext();
            ModuleRegistry registry = context.getRegistry();
            Module library = registry.addModule(name);
            Scope inner = library.getScope();
            
            statement.compile(inner);
         } catch(Exception cause) {
            return new ExceptionStatement("Error occured compiling '" + path + "'", cause);
         }
         return statement;
      }
   }
}
