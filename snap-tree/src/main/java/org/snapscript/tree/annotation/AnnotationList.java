/*
 * AnnotationList.java December 2016
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

package org.snapscript.tree.annotation;

import java.util.List;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.property.Property;

public class AnnotationList {
   
   private final AnnotationDeclaration[] list;

   public AnnotationList(AnnotationDeclaration... list) {
      this.list = list;
   }

   public void apply(Scope scope, Module module) throws Exception {
      List<Annotation> annotations = module.getAnnotations();
      
      for(AnnotationDeclaration entry : list) {
         Value value = entry.evaluate(scope, null);
         Annotation annotation = value.getValue();
         
         annotations.add(annotation);
      }
   }
   
   public void apply(Scope scope, Type type) throws Exception {
      List<Annotation> annotations = type.getAnnotations();
      
      for(AnnotationDeclaration entry : list) {
         Value value = entry.evaluate(scope, null);
         Annotation annotation = value.getValue();
         
         annotations.add(annotation);
      }
   }
   
   public void apply(Scope scope, Property property) throws Exception {
      List<Annotation> annotations = property.getAnnotations();
      
      for(AnnotationDeclaration entry : list) {
         Value value = entry.evaluate(scope, null);
         Annotation annotation = value.getValue();
         
         annotations.add(annotation);
      }
   }
   
   public void apply(Scope scope, Function function) throws Exception {
      List<Annotation> annotations = function.getAnnotations();
      
      for(AnnotationDeclaration entry : list) {
         Value value = entry.evaluate(scope, null);
         Annotation annotation = value.getValue();
         
         annotations.add(annotation);
      }
   }
   
   public void apply(Scope scope, Parameter parameter) throws Exception {
      List<Annotation> annotations = parameter.getAnnotations();
      
      for(AnnotationDeclaration entry : list) {
         Value value = entry.evaluate(scope, null);
         Annotation annotation = value.getValue();
         
         annotations.add(annotation);
      }
   }
}
