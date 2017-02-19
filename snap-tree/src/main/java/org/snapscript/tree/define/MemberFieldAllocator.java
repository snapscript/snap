/*
 * MemberFieldAllocator.java December 2016
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

import org.snapscript.core.Evaluation;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.tree.DeclarationAllocator;
import org.snapscript.tree.constraint.Constraint;

public class MemberFieldAllocator extends DeclarationAllocator {

   public MemberFieldAllocator(Constraint constraint, Evaluation expression) {   
      super(constraint, expression);
   }
   
   @Override
   protected Value create(Scope scope, Object value, Type type, int modifiers) throws Exception {
      if(ModifierType.isConstant(modifiers)) {
         return ValueType.getBlank(value, type, modifiers);
      }
      return ValueType.getProperty(value, type, modifiers);
   }
}
