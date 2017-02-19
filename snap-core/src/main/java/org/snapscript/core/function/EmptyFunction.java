/*
 * EmptyFunction.java December 2016
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

package org.snapscript.core.function;

import static org.snapscript.core.ModifierType.ABSTRACT;
import static org.snapscript.core.Reserved.METHOD_CLOSURE;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Type;
import org.snapscript.core.annotation.Annotation;

public class EmptyFunction<T> implements Function<T> {

   private final FunctionDescription description;
   private final List<Annotation> annotations;
   private final Signature signature;
   private final String name;
   private final int modifiers;

   public EmptyFunction(Signature signature){
      this(signature, METHOD_CLOSURE);
   }
   
   public EmptyFunction(Signature signature, String name){
      this(signature, name, ABSTRACT.mask);
   }
   
   public EmptyFunction(Signature signature, String name, int modifiers){
      this.description = new FunctionDescription(signature, null, name);
      this.annotations = new ArrayList<Annotation>();
      this.signature = signature;
      this.modifiers = modifiers;
      this.name = name;
   }
   
   @Override
   public int getModifiers(){
      return modifiers;
   }
   
   @Override
   public Type getHandle() {
      return null;
   }
   
   @Override
   public Type getConstraint() {
      return null;
   }
   
   @Override
   public Type getType() {
      return null;
   }
   
   @Override
   public String getName(){
      return name;
   }
   
   @Override
   public Signature getSignature(){
      return signature;
   }
   
   @Override
   public List<Annotation> getAnnotations() {
      return annotations;
   }
   
   @Override
   public Invocation<T> getInvocation(){
      return null;
   }
   
   @Override
   public String getDescription() {
      return description.getDescription();
   }
   
   @Override
   public String toString(){
      return description.toString();
   }
}
