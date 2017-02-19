/*
 * ArrayConstraint.java December 2016
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

package org.snapscript.tree.constraint;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.parse.StringToken;
import org.snapscript.tree.reference.TypeReference;

public class ArrayConstraint implements Evaluation {

   private final TypeReference reference;
   private final StringToken[] bounds;
   
   public ArrayConstraint(TypeReference reference, StringToken... bounds) {
      this.reference = reference;
      this.bounds = bounds;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Value value = reference.evaluate(scope, null);
      Type entry = value.getValue();
      Type array = create(scope, entry);
      
      return ValueType.getTransient(array);
   }
   
   private Type create(Scope scope, Type entry) throws Exception {
      Module module = entry.getModule();
      Context context = module.getContext();
      TypeLoader loader = context.getLoader();
      String prefix = module.getName();
      String name = entry.getName();
      
      return loader.resolveType(prefix, name, bounds.length);
   }
}
