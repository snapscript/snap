/*
 * MemberConstructor.java December 2016
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

import java.util.List;

import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.function.Function;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.function.ParameterList;

public abstract class MemberConstructor implements TypePart {
   
   private final ConstructorAssembler assembler;
   private final AnnotationList annotations;
   private final ModifierList list;
   private final Statement body;
   
   public MemberConstructor(AnnotationList annotations, ModifierList list, ParameterList parameters, Statement body){  
      this(annotations, list, parameters, null, body);
   }  
   
   public MemberConstructor(AnnotationList annotations, ModifierList list, ParameterList parameters, TypePart part, Statement body){  
      this.assembler = new ConstructorAssembler(parameters, part, body);
      this.annotations = annotations;
      this.list = list;
      this.body = body;
   } 
   
   @Override
   public Initializer define(Initializer initializer, Type type) throws Exception {
      return null;
   }

   protected Initializer compile(Initializer initializer, Type type, boolean compile) throws Exception {
      int modifiers = list.getModifiers();
      ConstructorBuilder builder = assembler.assemble(initializer, type);
      Function constructor = builder.create(initializer, type, modifiers, compile);
      List<Function> functions = type.getFunctions();
      Scope scope = type.getScope();
      
      annotations.apply(scope, constructor);
      functions.add(constructor);
      body.compile(scope);
      
      return null;
   }
}