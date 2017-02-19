/*
 * RelationalOperator.java December 2016
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
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.parse.StringToken;

public enum RelationalOperator {
   SAME("==="){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         Object first = left.getValue();
         Object second = right.getValue();
         
         if(first == second) {
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   },   
   NOT_SAME("!=="){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         Object first = left.getValue();
         Object second = right.getValue();
         
         if(first != second) {
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   },   
   EQUALS("=="){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         ValueComparator comparator = ValueComparator.resolveComparator(left, right);
         
         if(comparator.compare(left, right) == 0){
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   },  
   NOT_EQUALS("!="){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         ValueComparator comparator = ValueComparator.resolveComparator(left, right);
         
         if(comparator.compare(left, right) != 0){
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   },  
   GREATER(">"){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         ValueComparator comparator = ValueComparator.resolveComparator(left, right);
         
         if(comparator.compare(left, right) > 0){
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   },  
   GREATER_OR_EQUALS(">="){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         ValueComparator comparator = ValueComparator.resolveComparator(left, right);
         
         if(comparator.compare(left, right) >= 0){
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   }, 
   LESS("<"){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         ValueComparator comparator = ValueComparator.resolveComparator(left, right);
         
         if(comparator.compare(left, right) < 0){
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   }, 
   LESS_OR_EQUALS("<="){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         ValueComparator comparator = ValueComparator.resolveComparator(left, right);
         
         if(comparator.compare(left, right) <= 0){
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   },
   INSTANCE_OF("instanceof"){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         Object first = left.getValue();
         Object second = right.getValue();
         
         if(checker.instanceOf(scope, first, second)){
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   },
   NOT_INSTANCE_OF("!instanceof"){
      @Override
      public Value operate(Scope scope, Value left, Value right) {
         Object first = left.getValue();
         Object second = right.getValue();
         
         if(!checker.instanceOf(scope, first, second)){
            return BooleanValue.TRUE;
         }
         return BooleanValue.FALSE;
      }      
   };
   
   public final InstanceChecker checker;
   public final String operator;
   
   private RelationalOperator(String operator) {
      this.checker = new InstanceChecker();
      this.operator = operator;
   }
   
   public abstract Value operate(Scope scope, Value left, Value right);
   
   public static RelationalOperator resolveOperator(StringToken token) {
      if(token != null) {
         String value = token.getValue();
         RelationalOperator[] operators = RelationalOperator.values();
         
         for(RelationalOperator operator : operators) {
            if(operator.operator.equals(value)) {
               return operator;
            }
         }
      }
      return null;
   }   
}
