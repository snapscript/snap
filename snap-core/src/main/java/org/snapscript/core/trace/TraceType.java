/*
 * TraceType.java December 2016
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

public enum TraceType {
   CONSTRUCT,
   INVOKE,
   NORMAL,
   NATIVE;
   
   public static Trace getNative(Module module, Path path) {
      return new Trace(NATIVE, module, path, -2); // see StackTraceElement.isNativeMethod
   }
   
   public static Trace getConstruct(Module module, Path path, int line) {
      return new Trace(CONSTRUCT, module, path, line);
   }
   
   public static Trace getInvoke(Module module, Path path, int line) {
      return new Trace(INVOKE, module, path, line);
   }
   
   public static Trace getNormal(Module module, Path path, int line) {
      return new Trace(NORMAL, module, path, line);
   }
}
