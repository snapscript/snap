/*
 * Declaration.java December 2016
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
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.literal.TextLiteral;

public class Declaration {

   private final DeclarationAllocator allocator;
   private final NameExtractor extractor;
   
   public Declaration(TextLiteral identifier) {
      this(identifier, null, null);
   }
   
   public Declaration(TextLiteral identifier, Constraint constraint) {      
      this(identifier, constraint, null);
   }
   
   public Declaration(TextLiteral identifier, Evaluation value) {
      this(identifier, null, value);
   }
   
   public Declaration(TextLiteral identifier, Constraint constraint, Evaluation value) {
      this.allocator = new DeclarationAllocator(constraint, value);
      this.extractor = new NameExtractor(identifier);
   }   

   public Value create(Scope scope, int modifiers) throws Exception {
      String name = extractor.extract(scope);
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
