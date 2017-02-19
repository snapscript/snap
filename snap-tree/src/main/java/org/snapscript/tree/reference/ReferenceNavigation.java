/*
 * ReferenceNavigation.java December 2016
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

package org.snapscript.tree.reference;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.parse.StringToken;

public class ReferenceNavigation implements Evaluation {
   
   private final ReferenceOperator operator;
   private final Evaluation part;
   private final Evaluation next;
   
   public ReferenceNavigation(Evaluation part) {
      this(part, null, null);
   }
   
   public ReferenceNavigation(Evaluation part, StringToken operator, Evaluation next) {
      this.operator = ReferenceOperator.resolveOperator(operator);
      this.part = part;
      this.next = next;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Value value = part.evaluate(scope, left);         
 
      if(next != null) {
         return operator.operate(scope, next, value);
      }
      return value;
   }      
}