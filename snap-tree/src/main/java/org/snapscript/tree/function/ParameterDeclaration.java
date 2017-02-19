/*
 * ParameterDeclaration.java December 2016
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

package org.snapscript.tree.function;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.function.Parameter;
import org.snapscript.tree.Modifier;
import org.snapscript.tree.NameExtractor;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.constraint.Constraint;

public class ParameterDeclaration {
   
   private AnnotationList annotations;
   private NameExtractor extractor;
   private Constraint constraint;
   private Parameter parameter;
   private Modifier modifier;
   
   public ParameterDeclaration(AnnotationList annotations, Evaluation identifier){
      this(annotations, identifier, null, null);
   }
   
   public ParameterDeclaration(AnnotationList annotations, Evaluation identifier, Constraint constraint){
      this(annotations, identifier, null, constraint);
   }
   
   public ParameterDeclaration(AnnotationList annotations, Evaluation identifier, Modifier modifier){
      this(annotations, identifier, modifier, null);
   }
   
   public ParameterDeclaration(AnnotationList annotations, Evaluation identifier, Modifier modifier, Constraint constraint){
      this.extractor = new NameExtractor(identifier);
      this.annotations = annotations;
      this.constraint = constraint;
      this.modifier = modifier;
   }

   public Parameter get(Scope scope) throws Exception {
      if(parameter == null) {
         parameter = create(scope);
         
         if(parameter != null) {
            annotations.apply(scope, parameter);
         }
      }
      return parameter;
   }
   
   private Parameter create(Scope scope) throws Exception {
      String name = extractor.extract(scope);
      
      if(constraint != null && name != null) {
         Value value = constraint.evaluate(scope, null);
         Type type = value.getValue();
         
         if(type == null) {
            throw new InternalStateException("Constraint for '" +name + "' has not been imported");
         }
         return new Parameter(name, type, modifier != null);
      }
      return new Parameter(name, null, modifier != null);
   }
}  