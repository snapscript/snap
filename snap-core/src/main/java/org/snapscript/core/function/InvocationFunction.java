/*
 * InvocationFunction.java December 2016
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

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Type;
import org.snapscript.core.annotation.Annotation;

public class InvocationFunction<T> implements Function<T> {

   private final FunctionDescription description;
   private final List<Annotation> annotations;
   private final Invocation<T> invocation;
   private final Signature signature;
   private final Type constraint;
   private final Type parent;
   private final String name;
   private final int modifiers;

   public InvocationFunction(Signature signature, Invocation<T> invocation, Type parent, Type constraint, String name){
      this(signature, invocation, parent, constraint, name, 0);
   }
   
   public InvocationFunction(Signature signature, Invocation<T> invocation, Type parent, Type constraint, String name, int modifiers){
      this(signature, invocation, parent, constraint, name, modifiers, 0);
   }
   
   public InvocationFunction(Signature signature, Invocation<T> invocation, Type parent, Type constraint, String name, int modifiers, int start){
      this.description = new FunctionDescription(signature, parent, name, start);
      this.annotations = new ArrayList<Annotation>();
      this.invocation = invocation;
      this.constraint = constraint;
      this.signature = signature;
      this.modifiers = modifiers;
      this.parent = parent;
      this.name = name;
   }
   
   @Override
   public int getModifiers(){
      return modifiers;
   }
   
   @Override
   public Type getType() {
      return parent;
   }
   
   @Override
   public Type getHandle() {
      return signature.getDefinition();
   }
   
   @Override
   public Type getConstraint() {
      return constraint;
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
      return invocation;
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
