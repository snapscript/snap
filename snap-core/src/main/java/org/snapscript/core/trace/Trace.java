/*
 * Trace.java December 2016
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

package org.snapscript.core.trace;

import org.snapscript.core.Module;
import org.snapscript.core.Path;

public class Trace {
   
   private final TraceType type;
   private final Module module;
   private final Path path;
   private final int line;
   
   public Trace(TraceType type, Module module, Path path, int line) {
      this.module = module;
      this.path = path;
      this.line = line;
      this.type = type;
   }

   public TraceType getType() {
      return type;
   }
   
   public Module getModule(){
      return module;
   }

   public Path getPath() {
      return path;
   }

   public int getLine() {
      return line;
   }
   
   @Override
   public String toString() {
      return String.format("%s:%s", path, line);
   }
}
