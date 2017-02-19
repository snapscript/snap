/*
 * PrefixOperator.java December 2016
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

import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.parse.StringToken;

public enum PrefixOperator {
   NOT("!"){
      @Override
      public Value operate(Value right) { 
         Boolean value = right.getBoolean();         
         return ValueType.getTransient(!value);
      }      
   }, 
   COMPLEMENT("~"){
      @Override
      public Value operate(Value right) {
         Number value = right.getNumber(); 
         NumericConverter converter = NumericConverter.resolveConverter(value);   
         Long number = value.longValue();
         
         return converter.convert(~number);
      }      
   },
   PLUS("+"){
      @Override
      public Value operate(Value right) {
         Number value = right.getNumber(); 
         NumericConverter converter = NumericConverter.resolveConverter(value);   
         Double number = value.doubleValue();
         
         return converter.convert(+number);
      }      
   },
   MINUS("-"){
      @Override
      public Value operate(Value right) { 
         Number value = right.getNumber(); 
         NumericConverter converter = NumericConverter.resolveConverter(value);   
         Double number = value.doubleValue();
         
         return converter.convert(-number);
      }      
   };
   
   public final String operator;
   
   private PrefixOperator(String operator){
      this.operator = operator;
   }
   
   public abstract Value operate(Value right);   
   
   public static PrefixOperator resolveOperator(StringToken token) {
      if(token != null) {
         String value = token.getValue();
         PrefixOperator[] operators = PrefixOperator.values();
         
         for(PrefixOperator operator : operators) {
            if(operator.operator.equals(value)) {
               return operator;
            }
         }
      }
      return null;
   }  
}
