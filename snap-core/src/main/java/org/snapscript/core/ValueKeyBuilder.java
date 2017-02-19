/*
 * ValueKeyBuilder.java December 2016
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

package org.snapscript.core;

public class ValueKeyBuilder {
   
   public ValueKeyBuilder() {
      super();
   }

   public Object create(Scope scope, Object left, String name) throws Exception {
      if(left != null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         TypeExtractor extractor = context.getExtractor();
         Type type = extractor.getType(left);
         
         if(type != null) {
            return new ValueKey(name, type);
         }
      }
      return name;
   }
}
