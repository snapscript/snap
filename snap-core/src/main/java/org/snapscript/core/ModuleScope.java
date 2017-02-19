/*
 * ModuleScope.java December 2016
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

package org.snapscript.core;

public class ModuleScope implements Scope {
   
   private final Module module;
   private final State state;
   
   public ModuleScope(Module module) {
      this.state = new MapState(null);
      this.module = module;
   }
   
   @Override
   public Scope getInner() {
      return new CompoundScope(null, this, this);
   } 
   
   @Override
   public Scope getOuter() {
      return this;
   }

   @Override
   public State getState() {
      return state;
   }
   
   @Override
   public Model getModel() {
      return null;
   }
   
   @Override
   public Type getHandle() {
      return null;
   }
   
   @Override
   public Type getType() {
      return null;
   }  

   @Override
   public Module getModule() {
      return module;
   }
   
   @Override
   public String toString() {
      return String.valueOf(state);
   }
}
