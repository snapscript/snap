/*
 * MemberFunctionAssembler.java December 2016
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
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.ModifierChecker;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.NameExtractor;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.constraint.ConstraintExtractor;
import org.snapscript.tree.function.ParameterList;

public class MemberFunctionAssembler {
   
   private final ConstraintExtractor constraint;
   private final ParameterList parameters;
   private final ModifierChecker checker;
   private final NameExtractor extractor;
   private final ModifierList list;
   private final Statement body;
   
   public MemberFunctionAssembler(ModifierList list, Evaluation identifier, ParameterList parameters, Constraint constraint, Statement body){ 
      this.constraint = new ConstraintExtractor(constraint);
      this.extractor = new NameExtractor(identifier);
      this.checker = new ModifierChecker(list);
      this.parameters = parameters;
      this.list = list;
      this.body = body;
   } 

   public MemberFunctionBuilder assemble(Type type, int mask) throws Exception {
      Scope scope = type.getScope();
      String name = extractor.extract(scope);
      Signature signature = parameters.create(scope);
      Type returns = constraint.extract(scope);
      int modifiers = mask | list.getModifiers();
      
      if(checker.isStatic()) {
         return new StaticFunctionBuilder(signature, body, returns, name, modifiers);
      }
      return new InstanceFunctionBuilder(signature, body, returns, name, modifiers);
      
   }
}
