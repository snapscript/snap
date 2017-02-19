/*
 * Combination.java December 2016
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

import org.snapscript.core.BooleanValue;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.parse.StringToken;

public class Combination implements Evaluation {
   
   private final ConditionalOperator operator;
   private final Evaluation right;
   private final Evaluation left;
   
   public Combination(Evaluation left) {
      this(left, null, null);
   }
   
   public Combination(Evaluation left, StringToken operator, Evaluation right) {
      this.operator = ConditionalOperator.resolveOperator(operator);
      this.right = right;
      this.left = left;
   }
   
   @Override
   public Value evaluate(Scope scope, Object context) throws Exception { 
      Value first = evaluate(scope, left);
      
      if(first == BooleanValue.TRUE) {
         if(operator != null) {
            if(operator.isAnd()) {
               return evaluate(scope, right);
            }
         }
      } else {
         if(operator != null) {
            if(operator.isOr()) {
               return evaluate(scope, right);
            }
         }
      }
      return first;
   }
   
   private Value evaluate(Scope scope, Evaluation evaluation) throws Exception { 
      Value value = evaluation.evaluate(scope, null);
      Boolean result = value.getBoolean();
      
      if(result.booleanValue()) {
         return BooleanValue.TRUE;
      }
      return BooleanValue.FALSE;
   } 
}