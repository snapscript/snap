/*
 * OperationProcessor.java December 2016
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

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.FilePathConverter;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.Path;
import org.snapscript.core.PathConverter;
import org.snapscript.parse.Line;

public class OperationProcessor {

   private final PathConverter converter;
   private final Context context;
   
   public OperationProcessor(Context context) {
      this.converter = new FilePathConverter();
      this.context = context;
   }
   
   public Object process(Object value, Line line) throws Exception {
      if(Compilation.class.isInstance(value)) {
         Compilation compilation = (Compilation)value;
         String resource = line.getResource();
         Path path = converter.createPath(resource);
         String name = converter.createModule(resource);
         ModuleRegistry registry = context.getRegistry();
         Module module = registry.addModule(name);
         int number = line.getNumber();
         
         return compilation.compile(module, path, number);
      }
      return value;
   }
}
