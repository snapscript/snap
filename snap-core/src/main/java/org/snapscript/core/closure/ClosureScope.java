/*
 * ClosureScope.java December 2016
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

package org.snapscript.core.closure;

import org.snapscript.core.CompoundScope;
import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;

public class ClosureScope implements Scope {
   
   private final State state;
   private final Scope scope;
   private final Model model;
   
   public ClosureScope(Model model, Scope scope) {
      this.state = new ClosureState(scope);
      this.scope = scope;
      this.model = model;
   }

   @Override
   public Scope getInner() {
      return new CompoundScope(model, this, scope);
   }
   
   @Override
   public Scope getOuter() {
      return scope;
   }
   
   @Override
   public Type getHandle() {
      return scope.getType();
   }

   @Override
   public Type getType() {
      return scope.getType();
   }

   @Override
   public Module getModule() {
      return scope.getModule();
   }
   
   @Override
   public Model getModel() {
      return model;
   }

   @Override
   public State getState() {
      return state;
   }
   
   @Override
   public String toString() {
      return String.valueOf(state);
   }
}