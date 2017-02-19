/*
 * DeclarationAllocator.java December 2016
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

package org.snapscript.tree;

import org.snapscript.core.Evaluation;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.constraint.ConstraintExtractor;

public class DeclarationAllocator {

   private final DeclarationConverter converter;
   private final ConstraintExtractor extractor;
   private final Evaluation expression;
   
   public DeclarationAllocator(Constraint constraint, Evaluation expression) {      
      this.extractor = new ConstraintExtractor(constraint);
      this.converter = new DeclarationConverter();
      this.expression = expression;
   }   

   public Value allocate(Scope scope, String name, int modifiers) throws Exception {
      Type type = extractor.extract(scope);
      Object object = null;
      
      if(expression != null) {
         Value value = expression.evaluate(scope, null);
         Object original = value.getValue();
         
         if(type != null) {
            object = converter.convert(scope, original, type, name);
         } else {
            object = original;
         }
      }
      return create(scope, object, type, modifiers);
   }
   
   protected Value create(Scope scope, Object value, Type type, int modifiers) throws Exception {
      if(ModifierType.isConstant(modifiers)) {
         return ValueType.getConstant(value, type);
      }
      return ValueType.getReference(value, type);
   }
}
