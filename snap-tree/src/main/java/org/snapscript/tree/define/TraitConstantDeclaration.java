/*
 * TraitConstantDeclaration.java December 2016
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

import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.ModifierType.STATIC;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.tree.ModifierData;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.literal.TextLiteral;

public class TraitConstantDeclaration {
   
   private final MemberFieldDeclaration declaration;
   private final MemberFieldAssembler assembler;
   private final ModifierData modifiers;

   public TraitConstantDeclaration(TextLiteral identifier, Constraint constraint, Evaluation value) {
      this.declaration = new MemberFieldDeclaration(identifier, constraint, value);
      this.modifiers = new ModifierData(CONSTANT, STATIC);
      this.assembler = new MemberFieldAssembler(modifiers);
   }
   
   public Initializer declare(Initializer initializer, Type type) throws Exception {
      Scope scope = type.getScope();
      MemberFieldData data = declaration.create(scope);
      
      return assembler.assemble(data);
   }
}
