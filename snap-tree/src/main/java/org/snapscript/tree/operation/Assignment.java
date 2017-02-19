/*
 * Assignment.java December 2016
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

package org.snapscript.tree.operation;

import org.snapscript.core.convert.StringBuilder;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.parse.StringToken;

public class Assignment implements Evaluation {

   private final AssignmentOperator operator;
   private final Evaluation left;
   private final Evaluation right;
   
   public Assignment(Evaluation left, StringToken operator, Evaluation right) {
      this.operator = AssignmentOperator.resolveOperator(operator);
      this.left = left;
      this.right = right;
   }
   
   @Override
   public Value evaluate(Scope scope, Object context) throws Exception { // this is rubbish
      Value leftResult = left.evaluate(scope, context);
      Value rightResult = right.evaluate(scope, context);
      
      if(operator != AssignmentOperator.EQUAL) {
         Object leftValue = leftResult.getValue();
         
         if(!Number.class.isInstance(leftValue)) { 
            Object rightValue = rightResult.getValue();
            
            if(operator != AssignmentOperator.PLUS_EQUAL) {
               throw new InternalStateException("Operator " + operator + " is illegal");         
            }
            String leftText = StringBuilder.create(scope, leftValue);
            String rightText = StringBuilder.create(scope, rightValue);
            String text = leftText.concat(rightText);
            
            leftResult.setValue(text);
            return leftResult;
         }
      }
      return operator.operate(scope, leftResult, rightResult);      
   }
}