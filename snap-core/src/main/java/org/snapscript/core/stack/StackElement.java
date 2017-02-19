/*
 * StackElement.java December 2016
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

package org.snapscript.core.stack;

import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Type;
import org.snapscript.core.TypeNameBuilder;
import org.snapscript.core.function.Function;
import org.snapscript.core.trace.Trace;

public class StackElement {
   
   private static final String MAIN_FUNCTION = "main";
   
   private final TypeNameBuilder builder;
   private final Function function;
   private final Trace trace;
   
   public StackElement(Trace trace) {
      this(trace, null);
   }
   
   public StackElement(Trace trace, Function function) {
      this.builder = new TypeNameBuilder();
      this.function = function;
      this.trace = trace;
   }
   
   public StackTraceElement build() {
      Module module = trace.getModule();
      String name = module.getName();
      Path path = trace.getPath();
      int line = trace.getLine();
      
      return create(name, path, line);
   }
   
   private StackTraceElement create(String module, Path path, int line) {
      String resource = path.getPath();
      
      if(function != null) {
         String name = function.getName();
         Type type = function.getType();
         
         if(type != null) {
            Module parent = type.getModule();
            String prefix = parent.getName();
            String suffix = type.getName(); // module functions have no type name
            String qualifier = builder.createFullName(prefix, suffix);
            
            return new StackTraceElement(qualifier, name, resource, line);
         }
         return new StackTraceElement(module, name, resource, line);
      }
      return new StackTraceElement(module, MAIN_FUNCTION, resource, line);
   }
}