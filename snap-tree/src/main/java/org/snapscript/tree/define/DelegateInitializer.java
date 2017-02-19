/*
 * DelegateInitializer.java December 2016
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

package org.snapscript.tree.define;

import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.define.SuperExtractor;

public class DelegateInitializer {

   private final SuperConstructor constructor;
   private final SuperExtractor extractor;
   private final TypePart part;

   public DelegateInitializer(TypePart part){  
      this.constructor = new SuperConstructor();
      this.extractor = new SuperExtractor();
      this.part = part;
   } 

   public Initializer compile(Initializer initializer, Type type) throws Exception {
      Type base = extractor.extractor(type);
      
      if(part != null){
         return part.compile(initializer, type);              
      }
      if(base != null) {
         return constructor.compile(initializer, type);
      }
      return new PrimitiveConstructor(); 
   }
}
