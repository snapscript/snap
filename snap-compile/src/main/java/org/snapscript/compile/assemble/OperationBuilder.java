/*
 * OperationBuilder.java December 2016
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

import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;
import static org.snapscript.core.Reserved.DEFAULT_RESOURCE;
import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.ContextModule;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.parse.Line;

public class OperationBuilder {
   
   private final OperationProcessor processor;
   private final Module module;
   private final Path path;

   public OperationBuilder(Context context) {
      this.path = new Path(DEFAULT_RESOURCE);
      this.module = new ContextModule(context, path, DEFAULT_PACKAGE, 0);
      this.processor = new OperationProcessor(context);
   }
   
   public Object create(Type type, Object[] arguments, Line line) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      Callable<Result> callable = binder.bind(scope, type, TYPE_CONSTRUCTOR, arguments);
      
      if(callable == null) {
         throw new InternalStateException("No constructor for '" + type + "' at line " + line);
      }
      Result result = callable.call();
      Object value = result.getValue();
      
      return processor.process(value, line);
   }


}
