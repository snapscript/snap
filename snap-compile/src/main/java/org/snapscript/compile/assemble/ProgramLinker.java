/*
 * ProgramLinker.java December 2016
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

import static org.snapscript.tree.Instruction.SCRIPT_PACKAGE;

import org.snapscript.common.Cache;
import org.snapscript.common.LeastRecentlyUsedCache;
import org.snapscript.core.Context;
import org.snapscript.core.Path;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageLinker;

public class ProgramLinker implements PackageLinker {
   
   private final Cache<Path, Package> cache;
   private final PackageBuilder builder;  
   
   public ProgramLinker(Context context) {
      this.cache = new LeastRecentlyUsedCache<Path, Package>();
      this.builder = new PackageBuilder(context);
   }
   
   @Override
   public Package link(Path path, String source) throws Exception {
      return link(path, source, SCRIPT_PACKAGE.name);
   }
   
   @Override
   public Package link(Path path, String source, String grammar) throws Exception {
      Package linked = cache.fetch(path);
      
      if(linked == null) {
         linked = builder.create(path, source, grammar); 
         cache.cache(path, linked);
      }
      return linked; 
   } 
}
