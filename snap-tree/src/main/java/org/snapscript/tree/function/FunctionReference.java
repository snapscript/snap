/*
 * FunctionReference.java December 2016
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
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.function.Function;
import org.snapscript.tree.NameExtractor;

public class FunctionReference implements Evaluation {
   
   private final FunctionReferenceBuilder builder;
   private final NameExtractor extractor;
   private final Evaluation variable;
   
   public FunctionReference(Evaluation variable, Evaluation method) {
      this.builder = new FunctionReferenceBuilder();
      this.extractor = new NameExtractor(method);
      this.variable = variable;
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Value value = variable.evaluate(scope, left);
      String match = extractor.extract(scope);
      Module module = scope.getModule(); // is this the correct module?
      Object object = value.getValue();
      Function function = builder.create(module, object, match); 
      
      return ValueType.getTransient(function);
   }
}
