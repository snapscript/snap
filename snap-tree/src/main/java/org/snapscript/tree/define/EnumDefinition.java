/*
 * EnumDefinition.java December 2016
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

public class EnumDefinition extends Statement {

   private final DefaultConstructor constructor;
   private final InitializerCollector collector;
   private final AtomicBoolean compile;
   private final EnumBuilder builder;
   private final EnumList list;
   private final TypePart[] parts;
   
   public EnumDefinition(AnnotationList annotations, TypeName name, TypeHierarchy hierarchy, EnumList list, TypePart... parts) {
      this.builder = new EnumBuilder(name, hierarchy);
      this.constructor = new DefaultConstructor(true);
      this.collector = new InitializerCollector();
      this.compile = new AtomicBoolean(true);
      this.parts = parts;
      this.list = list;
   }
   
   @Override
   public Result define(Scope outer) throws Exception {
      return builder.define(outer);
   }

   @Override
   public Result compile(Scope outer) throws Exception {
      if(!compile.compareAndSet(false, true)) {
         Result result = builder.compile(outer);
         Type type = result.getValue();
         Initializer keys = list.compile(collector, type);
         Scope scope = type.getScope();
         
         for(TypePart part : parts) {
            Initializer initializer = part.compile(collector, type);
            collector.update(initializer);
         }  
         constructor.compile(collector, type); 
         keys.execute(scope, type);
         collector.compile(scope, type); 
         
         return result;
      }
      return ResultType.getNormal();
   }
}
