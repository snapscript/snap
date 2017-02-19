/*
 * CompoundScope.java December 2016
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

public class CompoundScope implements Scope {
   
   private final State state;
   private final Scope outer;
   private final Model model;
   
   public CompoundScope(Model model, Scope inner, Scope outer) {
      this.state = new MapState(model, inner);  
      this.outer = outer;
      this.model = model;
   } 
  
   @Override
   public Scope getInner() {
      return new StateScope(model, this, outer);
   }  
   
   @Override
   public Scope getOuter() {
      return outer;
   }  
   
   @Override
   public Type getHandle() {
      return outer.getType();
   }
   
   @Override
   public Type getType() {
      return outer.getType();
   }
  
   @Override
   public Module getModule() {
      return outer.getModule();
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
   
   private static class StateScope implements Scope {
      
      private final State state;
      private final Scope outer;
      private final Model model;
      
      public StateScope(Model model, Scope inner, Scope outer) {
         this.state = new MapState(null, inner); // ignore model
         this.outer = outer;
         this.model = model;
      }

      @Override
      public Scope getInner() {
         return new StateScope(model, this, outer);
      }
      
      @Override
      public Scope getOuter() {
         return outer;
      }
      
      @Override
      public Type getHandle() {
         return outer.getType();
      }

      @Override
      public Type getType() {
         return outer.getType();
      }

      @Override
      public Module getModule() {
         return outer.getModule();
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
}
