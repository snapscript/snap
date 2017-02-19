/*
 * SuperExtractor.java December 2016
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

package org.snapscript.core.define;

import java.util.List;

import org.snapscript.core.Type;

public class SuperExtractor {
   
   public SuperExtractor() {
      super();
   }
   
   public Type extractor(Type type) {
      List<Type> types = type.getTypes();
      
      for(Type base : types) {
         return base;
      }
      return null;
   }

}
