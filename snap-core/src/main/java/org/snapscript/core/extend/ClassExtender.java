/*
 * ClassExtender.java December 2016
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

package org.snapscript.core.extend;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.TypeLoader;
import org.snapscript.core.function.Function;

public class ClassExtender {
   
   private final ExtensionRegistry registry;
   private final AtomicBoolean done;
   
   public ClassExtender(TypeLoader loader) {
      this.registry = new ExtensionRegistry(loader);
      this.done = new AtomicBoolean();
   }
   
   public List<Function> extend(Class type){
      if(done.compareAndSet(false, true)) {
         registry.register(File.class, FileExtension.class);
         registry.register(Date.class, DateExtension.class);
      }
      return registry.extract(type);
   }

}
