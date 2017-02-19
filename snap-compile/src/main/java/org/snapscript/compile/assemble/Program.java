/*
 * Program.java December 2016
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

package org.snapscript.compile.assemble;

import org.snapscript.compile.Executable;
import org.snapscript.core.Context;
import org.snapscript.core.EmptyModel;
import org.snapscript.core.Model;
import org.snapscript.core.Path;
import org.snapscript.core.ProgramValidator;
import org.snapscript.core.Scope;
import org.snapscript.core.ScopeMerger;
import org.snapscript.core.Statement;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageDefinition;

public class Program implements Executable{
   
   private final ScopeMerger merger;
   private final Package library;
   private final Context context;
   private final Model model;
   private final String name;
   private final Path path;
   
   public Program(Context context, Package library, Path path, String name){
      this.merger = new ScopeMerger(context);
      this.model = new EmptyModel();
      this.library = library;
      this.context = context;
      this.path = path;
      this.name = name;
   }
   
   @Override
   public void execute() throws Exception {
      execute(model);
   }
   
   @Override
   public void execute(Model model) throws Exception{ 
      Scope scope = merger.merge(model, name, path);
      PackageDefinition definition = library.define(scope); // define all types
      Statement statement = definition.compile(scope, null); // compile tree
      ProgramValidator validator = context.getValidator();
      ErrorHandler handler = context.getHandler();
      
      try {
         validator.validate(context); // validate program
         statement.execute(scope);
      } catch(Throwable cause) {
         handler.throwExternal(scope, cause);
      }
   }
}
