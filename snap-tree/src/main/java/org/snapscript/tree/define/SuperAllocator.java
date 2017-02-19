/*
 * SuperAllocator.java December 2016
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

import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.define.Instance;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.SignatureAligner;
import org.snapscript.tree.function.ParameterExtractor;

public class SuperAllocator implements Allocator {
   
   private final ParameterExtractor extractor;
   private final SignatureAligner aligner;
   private final Initializer initializer;
   private final Allocator allocator;
   
   public SuperAllocator(Signature signature, Initializer initializer, Allocator allocator) {
      this.extractor = new ParameterExtractor(signature);
      this.aligner = new SignatureAligner(signature);
      this.initializer = initializer;
      this.allocator = allocator;
   }

   @Override
   public Instance allocate(Scope scope, Instance object, Object... list) throws Exception {
      Type real = (Type)list[0];
      Object[] arguments = aligner.align(list);
      Scope inner = object.getInner();
      
      if(arguments.length > 0) {
         extractor.extract(inner, arguments);
      }
      Result result = initializer.execute(inner, real);
      Instance base = result.getValue();
      
      return allocator.allocate(scope, base, list);
   }
}
