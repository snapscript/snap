/*
 * ConstructorIndexer.java December 2016
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

package org.snapscript.core.index;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.snapscript.core.Type;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.annotation.AnnotationExtractor;
import org.snapscript.core.function.Function;

public class ConstructorIndexer {

   private final AnnotationExtractor extractor;
   private final ConstructorGenerator generator;
   private final ModifierConverter converter;
   
   public ConstructorIndexer(TypeIndexer indexer) {
      this.generator = new ConstructorGenerator(indexer);
      this.extractor = new AnnotationExtractor();
      this.converter = new ModifierConverter();
   }

   public List<Function> index(Type type) throws Exception {
      Class source = type.getType();
      
      if(source != Class.class) {
         Constructor[] constructors = source.getDeclaredConstructors();
         
         if(constructors.length > 0) {
            List<Function> functions = new ArrayList<Function>();
      
            for(Constructor constructor : constructors){
               int modifiers = converter.convert(constructor); // accept all consructors public/private
               Class[] parameters = constructor.getParameterTypes();
               Function function = generator.generate(type, constructor, parameters, modifiers);
               List<Annotation> extracted = extractor.extract(constructor);
               List<Annotation> actual = function.getAnnotations();
               
               functions.add(function);
               actual.addAll(extracted);
            }
            return functions;
         }
      }
      return Collections.emptyList();
   }
}
