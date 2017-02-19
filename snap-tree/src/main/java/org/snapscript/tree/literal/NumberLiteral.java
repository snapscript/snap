/*
 * NumberLiteral.java December 2016
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

package org.snapscript.tree.literal;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.parse.NumberToken;
import org.snapscript.parse.StringToken;
import org.snapscript.tree.operation.SignOperator;

public class NumberLiteral extends Literal {
   
   private final SignOperator operator;
   private final NumberToken token;

   public NumberLiteral(NumberToken token) {
      this(null, token);
   }
   
   public NumberLiteral(StringToken sign, NumberToken token) {
      this.operator = SignOperator.resolveOperator(sign);
      this.token = token;
   }

   @Override
   protected Value create(Scope scope) throws Exception {
      Number number = token.getValue();
      
      if(number == null) {
         throw new InternalStateException("Number value was null");
      }
      return operator.operate(number);
   }
}