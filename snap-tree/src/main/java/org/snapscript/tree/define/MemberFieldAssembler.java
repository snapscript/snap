/*
 * MemberFieldAssembler.java December 2016
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
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Identity;
import org.snapscript.core.Value;
import org.snapscript.core.define.Initializer;
import org.snapscript.tree.DeclarationAllocator;
import org.snapscript.tree.ModifierChecker;
import org.snapscript.tree.ModifierData;
import org.snapscript.tree.constraint.Constraint;

public class MemberFieldAssembler {
   
   private final ModifierChecker checker;

   public MemberFieldAssembler(ModifierData modifiers) {
      this.checker = new ModifierChecker(modifiers);
   }
   
   public Initializer assemble(MemberFieldData data) throws Exception {
      Evaluation declaration = create(data);
      
      if (checker.isStatic()) {
         return new StaticFieldInitializer(declaration);
      }
      return new InstanceFieldInitializer(declaration);
   }
   
   private Evaluation create(MemberFieldData data) throws Exception {
      int modifiers = checker.getModifiers();
      String name = data.getName();
      Type type = data.getConstraint();
      Evaluation declare = data.getValue();
      
      return new Declaration(name, type, declare, modifiers);
   }
   
   private static class Declaration implements Evaluation {
      
      private final DeclarationAllocator allocator;
      private final Constraint constraint;
      private final Evaluation value;
      private final String name;
      private final int modifiers;
      
      public Declaration(String name, Type type, Evaluation declare, int modifiers) {
         this.value = new Identity(type);
         this.constraint = new Constraint(value);
         this.allocator = new MemberFieldAllocator(constraint, declare);
         this.modifiers = modifiers;
         this.name = name;
      }   

      @Override
      public Value evaluate(Scope scope, Object left) throws Exception {
         Value value = allocator.allocate(scope, name, modifiers);
         State state = scope.getState();
         
         try { 
            state.add(name, value);
         }catch(Exception e) {
            throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
         }  
         return value;
      }
   }
}
