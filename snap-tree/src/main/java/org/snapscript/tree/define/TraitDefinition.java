/*
 * TraitDefinition.java December 2016
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

package org.snapscript.tree.define;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.tree.annotation.AnnotationList;

public class TraitDefinition extends Statement {   
   
   private final FunctionPropertyGenerator generator;
   private final InitializerCollector collector;
   private final Initializer constants;
   private final AtomicBoolean compile;
   private final AtomicBoolean define;
   private final ClassBuilder builder;
   private final TypePart[] parts;
   
   public TraitDefinition(AnnotationList annotations, TraitName name, TypeHierarchy hierarchy, TypePart... parts) {
      this.builder = new ClassBuilder(annotations, name, hierarchy);
      this.generator = new FunctionPropertyGenerator(); 
      this.constants = new StaticConstantInitializer();
      this.collector = new InitializerCollector();
      this.compile = new AtomicBoolean(true);
      this.define = new AtomicBoolean(true);
      this.parts = parts;
   }
   
   @Override
   public Result define(Scope outer) throws Exception {
      if(!define.compareAndSet(false, true)) {
         Result result = builder.define(outer);
         Type type = result.getValue();
         
         for(TypePart part : parts) {
            Initializer initializer = part.define(collector, type);
            collector.update(initializer);
         } 
         return result;
      }
      return ResultType.getNormal();
   }

   @Override
   public Result compile(Scope outer) throws Exception {
      if(!compile.compareAndSet(false, true)) {
         Result result = builder.compile(outer);
         Type type = result.getValue();
         
         collector.update(constants); // collect static constants first
         
         for(TypePart part : parts) {
            Initializer initializer = part.compile(collector, type);
            collector.update(initializer);
         } 
         generator.generate(type);
         
         return result;
      }
      return ResultType.getNormal();
   }

}
