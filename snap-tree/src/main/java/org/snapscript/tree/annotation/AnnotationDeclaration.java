/*
 * AnnotationDeclaration.java December 2016
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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.annotation.MapAnnotation;
import org.snapscript.tree.construct.MapEntryList;

public class AnnotationDeclaration implements Evaluation {

   private AnnotationName name;
   private MapEntryList list;
   private Value value;
   
   public AnnotationDeclaration(AnnotationName name) {
      this(name, null);
   }
   
   public AnnotationDeclaration(AnnotationName name, MapEntryList list) {
      this.list = list;
      this.name = name;
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      if(value == null) {
         Annotation annotation = create(scope, left);
         
         if(annotation == null) {
            throw new InternalStateException("Could not create annotation");
         }
         value = ValueType.getTransient(annotation);
      }
      return value;
   }
   
   private Annotation create(Scope scope, Object left) throws Exception {
      Map<String, Object> attributes = new LinkedHashMap<String, Object>();
      
      if(list != null) {
         Value value = list.evaluate(scope, left);
         Map<Object, Object> map = value.getValue();
         Set<Object> keys = map.keySet();
         
         for(Object key : keys) {
            String name = String.valueOf(key);
            Object attribute = map.get(name);
            
            attributes.put(name, attribute);
         }
      }
      Value value = name.evaluate(scope, left);
      String name = value.getString();
      
      return new MapAnnotation(name, attributes);
   }
}
