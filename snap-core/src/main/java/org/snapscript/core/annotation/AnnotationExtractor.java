/*
 * AnnotationExtractor.java December 2016
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

package org.snapscript.core.annotation;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;

public class AnnotationExtractor {
   
   private final AnnotationConverter converter;
   
   public AnnotationExtractor() {
      this.converter = new AnnotationConverter();
   }

   public List<Annotation> extract(AnnotatedElement element) throws Exception {
      List<Annotation> list = new ArrayList<Annotation>();

      if(element != null) {
         Object[] array = element.getAnnotations();
         
         for(Object entry : array) {
            Object result = converter.convert(entry);
            Annotation annotation = (Annotation)result;
            
            list.add(annotation);
         }
      }
      return list;  
   }
}
