/*
 * ConstantProperty.java December 2016
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

package org.snapscript.core.property;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Constant;
import org.snapscript.core.Type;
import org.snapscript.core.annotation.Annotation;

public class ConstantProperty implements Property<Object> {

   private final List<Annotation> annotations;
   private final Constant constant;
   private final Type constraint;
   private final Type type;
   private final String name;
   private final int modifiers;
   
   public ConstantProperty(String name, Type type, Type constraint, Object value, int modifiers){
      this.annotations = new ArrayList<Annotation>();
      this.constant = new Constant(value, type);
      this.constraint = constraint;
      this.modifiers = modifiers;
      this.name = name;
      this.type = type;
   }
   
   @Override
   public List<Annotation> getAnnotations(){
      return annotations;
   }
   
   @Override
   public Type getType(){
      return type;
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
   public int getModifiers() {
      return modifiers;
   }
   
   @Override
   public Object getValue(Object source) {
      return constant.getValue();
   }

   @Override
   public void setValue(Object source, Object value) {
      constant.setValue(value);
   }
   
   @Override
   public String toString(){
      return name;
   }
}
