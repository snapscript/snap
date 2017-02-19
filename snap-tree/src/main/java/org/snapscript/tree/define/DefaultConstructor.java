/*
 * DefaultConstructor.java December 2016
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

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import java.util.List;

import org.snapscript.core.NoStatement;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.function.Function;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.function.ParameterList;

public class DefaultConstructor implements TypePart {
   
   private final AnnotationList annotations;
   private final ParameterList parameters;
   private final ModifierList modifiers;
   private final boolean compile;

   public DefaultConstructor(){
      this(true);
   }
   
   public DefaultConstructor(boolean compile) {
      this.annotations = new AnnotationList();
      this.parameters = new ParameterList();
      this.modifiers = new ModifierList();
      this.compile = compile;
   } 
   
   @Override
   public Initializer define(Initializer initializer, Type type) throws Exception {
      return null;
   }
   
   @Override
   public Initializer compile(Initializer initializer, Type type) throws Exception {
      List<Function> functions = type.getFunctions();
      
      for(Function function : functions) {
         String name = function.getName();
         
         if(name.equals(TYPE_CONSTRUCTOR)) {
            return null;
         }
      }
      return define(initializer, type, compile);
   }
   
   protected Initializer define(Initializer initializer, Type type, boolean compile) throws Exception {
      Statement statement = new NoStatement();
      ClassConstructor constructor = new ClassConstructor(annotations, modifiers, parameters, statement);
      
      return constructor.compile(initializer, type, compile);
   }
}