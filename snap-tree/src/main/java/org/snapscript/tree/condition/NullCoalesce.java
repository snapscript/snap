/*
 * NullCoalesce.java December 2016
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

package org.snapscript.tree.condition;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;

public class NullCoalesce implements Evaluation {

   private final Evaluation substitute;
   private final Evaluation evaluation;
   
   public NullCoalesce(Evaluation evaluation, Evaluation substitute) {
      this.evaluation = evaluation;
      this.substitute = substitute;
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Value result = evaluation.evaluate(scope, null);
      Object value = result.getValue();
      System.out.println(value);
      if(value == null) {
         return substitute.evaluate(scope, left);
      } 
      return result;
   }
}
