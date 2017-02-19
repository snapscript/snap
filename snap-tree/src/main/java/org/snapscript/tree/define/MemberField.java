/*
 * MemberField.java December 2016
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
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.function.Accessor;
import org.snapscript.core.function.AccessorProperty;
import org.snapscript.core.function.ScopeAccessor;
import org.snapscript.core.function.StaticAccessor;
import org.snapscript.core.property.Property;
import org.snapscript.tree.ModifierChecker;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;

public class MemberField implements TypePart {
   
   private final MemberFieldDeclaration[] declarations;
   private final InitializerCollector collector;
   private final MemberFieldAssembler assembler;
   private final AnnotationList annotations;
   private final ModifierChecker checker;

   public MemberField(AnnotationList annotations, ModifierList modifiers, MemberFieldDeclaration... declarations) {
      this.assembler = new MemberFieldAssembler(modifiers);
      this.checker = new ModifierChecker(modifiers);
      this.collector = new InitializerCollector();
      this.declarations = declarations;
      this.annotations = annotations;
   }
   
   @Override
   public Initializer define(Initializer initializer, Type type) throws Exception {
      return null;
   }

   @Override
   public Initializer compile(Initializer initializer, Type type) throws Exception {
      Scope scope = type.getScope();
      List<Property> properties = type.getProperties();
      int mask = checker.getModifiers();
      
      for(MemberFieldDeclaration declaration : declarations) {
         MemberFieldData data = declaration.create(scope);
         String name = data.getName();
         Type constraint = data.getConstraint();
         Initializer declare = assembler.assemble(data);
         
         if (checker.isStatic()) {
            Accessor accessor = new StaticAccessor(initializer, scope, type, name);
            Property property = new AccessorProperty(name, type, constraint, accessor, mask);
            
            annotations.apply(scope, property);
            properties.add(property);
         } else {
            Accessor accessor = new ScopeAccessor(name);
            Property property = new AccessorProperty(name, type, constraint, accessor, mask); // is this the correct type!!??
            
            annotations.apply(scope, property);
            properties.add(property);
         }
         collector.update(declare);
      }
      return collector;
   }
}

