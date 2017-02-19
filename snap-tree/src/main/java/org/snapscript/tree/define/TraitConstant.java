/*
 * TraitConstant.java December 2016
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

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.function.Accessor;
import org.snapscript.core.function.AccessorProperty;
import org.snapscript.core.function.StaticAccessor;
import org.snapscript.core.property.Property;
import org.snapscript.tree.ModifierChecker;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.constraint.ConstraintExtractor;
import org.snapscript.tree.literal.TextLiteral;

public class TraitConstant implements TypePart {

   private final TraitConstantDeclaration declaration;
   private final ConstraintExtractor extractor;
   private final AnnotationList annotations;
   private final ModifierChecker checker;
   private final TextLiteral identifier;
   
   public TraitConstant(AnnotationList annotations, ModifierList list, TextLiteral identifier, Evaluation value) {
      this(annotations, list, identifier, null, value);
   }
   
   public TraitConstant(AnnotationList annotations, ModifierList list, TextLiteral identifier, Constraint constraint, Evaluation value) {
      this.declaration = new TraitConstantDeclaration(identifier, constraint, value);
      this.extractor = new ConstraintExtractor(constraint);
      this.checker = new ModifierChecker(list);
      this.annotations = annotations;
      this.identifier = identifier;
   }
   
   @Override
   public Initializer define(Initializer initializer, Type type) throws Exception {
      return null;
   }

   @Override
   public Initializer compile(Initializer initializer, Type type) throws Exception {
      Scope scope = type.getScope();
      Initializer declare = declaration.declare(initializer, type);
      List<Property> properties = type.getProperties();
      Value value = identifier.evaluate(scope, null);
      Type constraint = extractor.extract(scope);
      String name = value.getString();
      
      if(!checker.isConstant()) {
         throw new InternalStateException("Variable '" + name + "' for '" + type + "' must be constant");
      }
      Accessor accessor = new StaticAccessor(initializer, scope, type, name);
      Property property = new AccessorProperty(name, type, constraint, accessor, ModifierType.STATIC.mask | ModifierType.CONSTANT.mask);
      
      annotations.apply(scope, property);
      properties.add(property);

      return declare;
   }
}