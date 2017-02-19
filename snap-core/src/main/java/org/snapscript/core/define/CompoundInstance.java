/*
 * CompoundInstance.java December 2016
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

package org.snapscript.core.define;

import org.snapscript.core.MapState;
import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;

public class CompoundInstance implements Instance {
   
   private final Instance instance;
   private final Module module;
   private final State state;
   private final Model model;
   private final Scope outer;
   private final Type type;
   
   public CompoundInstance(Module module, Model model, Instance instance, Scope outer, Type type) {
      this.state = new MapState(model, outer);
      this.instance = instance;
      this.module = module;
      this.outer = outer;
      this.model = model;
      this.type = type;
   }
   
   @Override
   public Instance getInner() {
      return new CompoundInstance(module, model, instance, this, type);
   } 
   
   @Override
   public Instance getOuter() {
      return instance;
   } 
   
   @Override
   public Instance getSuper() {
      return instance.getSuper();
   }
  
   @Override
   public Module getModule() {
      return module;
   }
   
   @Override
   public Type getHandle(){
      return type;
   }
   
   @Override
   public Type getType(){
      return type;
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
   public String toString(){
      return outer.toString();
   }
}
