/*
 * Blank.java December 2016
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

import java.util.concurrent.atomic.AtomicReference;

public class Blank extends Value {
   
   private final AtomicReference<Object> reference;
   private final Type type;
   private final int modifiers;
   
   public Blank(Object value, Type type, int modifiers) {
      this.reference = new AtomicReference<Object>(value);
      this.modifiers = modifiers;
      this.type = type;
   }
   
   @Override
   public boolean isProperty() {
      return modifiers != -1;
   }
   
   @Override
   public int getModifiers() {
      return modifiers;
   }
   
   @Override
   public Type getConstraint() {
      return type;
   }
   
   @Override
   public <T> T getValue() {
      return (T)reference.get();
   }
   
   @Override
   public void setValue(Object value){
      if(!reference.compareAndSet(null, value)) {
         throw new InternalStateException("Illegal modification of constant");
      }
   } 
   
   @Override
   public String toString() {
      return String.valueOf(reference);
   }
}
