/*
 * ScopeFunctionMatcher.java December 2016
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

package org.snapscript.core.bind;

import static org.snapscript.core.convert.Score.INVALID;

import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;
import org.snapscript.core.convert.Score;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;
import org.snapscript.core.stack.ThreadStack;

public class ScopeFunctionMatcher {
   
   private final ThreadStack stack;
   
   public ScopeFunctionMatcher(ThreadStack stack) {
      this.stack = stack;
   }
   
   public FunctionPointer match(Scope scope, String name, Object... values) throws Exception { // match function variable
      State state = scope.getState();
      Value value = state.get(name);
      
      if(value != null) {
         Object object = value.getValue();
         
         if(Function.class.isInstance(object)) {
            Function function = (Function)object;
            Signature signature = function.getSignature();
            ArgumentConverter match = signature.getConverter();
            Score score = match.score(values);
            
            if(score.compareTo(INVALID) > 0) {
               return new FunctionPointer(function, stack, values);
            }
         }
      }
      return null;
   }
}
