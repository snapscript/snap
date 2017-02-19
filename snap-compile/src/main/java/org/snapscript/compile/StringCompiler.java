/*
 * StringCompiler.java December 2016
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

package org.snapscript.compile;

import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;
import static org.snapscript.tree.Instruction.SCRIPT;

import org.snapscript.common.Cache;
import org.snapscript.common.LeastRecentlyUsedCache;
import org.snapscript.compile.assemble.Program;
import org.snapscript.core.Context;
import org.snapscript.core.FilePathConverter;
import org.snapscript.core.Path;
import org.snapscript.core.PathConverter;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageLinker;

public class StringCompiler implements Compiler {
   
   private final Cache<String, Executable> cache;
   private final PathConverter converter;
   private final Context context;   
   private final String module;
   
   public StringCompiler(Context context) {
      this(context, DEFAULT_PACKAGE);
   }
   
   public StringCompiler(Context context, String module) {
      this.cache = new LeastRecentlyUsedCache<String, Executable>();
      this.converter = new FilePathConverter();
      this.context = context;
      this.module = module;
   } 
   
   @Override
   public Executable compile(String source) throws Exception {
      if(source == null) {
         throw new NullPointerException("No source provided");
      }
      Executable executable = cache.fetch(source);
      
      if(executable == null) {
         Path path = converter.createPath(module);
         PackageLinker linker = context.getLinker();
         Package library = linker.link(path, source, SCRIPT.name);
         
         return new Program(context, library, path, module);
      }
      return executable;
   } 
}
