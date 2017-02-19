/*
 * Parameter.java December 2016
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

public class Parameter {
   
   private final List<Annotation> annotations;
   private final String name;
   private final Type type;
   private final boolean variable;
   
   public Parameter(String name, Type type){
      this(name, type, false);
   }
   
   public Parameter(String name, Type type, boolean variable){
      this.annotations = new ArrayList<Annotation>();
      this.variable = variable;
      this.name = name;
      this.type = type;
   }
   
   public List<Annotation> getAnnotations() {
      return annotations;
   }
   
   public boolean isVariable() {
      return variable;
   }
   
   public String getName() {
      return name;
   }
   
   public Type getType() {
      return type;
   }
}  