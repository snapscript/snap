/*
 * Signature.java December 2016
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

import java.util.List;

import org.snapscript.core.Module;
import org.snapscript.core.Type;

public class Signature {
   
   private final List<Parameter> parameters;
   private final SignatureDescription description;
   private final SignatureMatcher matcher;
   private final Type definition;
   private final boolean variable;

   public Signature(List<Parameter> parameters, Module module){
      this(parameters, module, false);
   }
   
   public Signature(List<Parameter> parameters, Module module, boolean variable){
      this.description = new SignatureDescription(this);
      this.matcher = new SignatureMatcher(this, module);
      this.definition = new FunctionType(this, module);
      this.parameters = parameters;
      this.variable = variable;
   }
   
   public Type getDefinition() {
      return definition;
   }
   
   public ArgumentConverter getConverter() {
      return matcher.getConverter();
   }
   
   public List<Parameter> getParameters(){
      return parameters;
   }
   
   public boolean isVariable() {
      return variable;
   }
   
   @Override
   public String toString() {
      return description.toString();
   }
}