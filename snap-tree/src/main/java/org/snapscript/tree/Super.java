/*
 * Super.java December 2016
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

package org.snapscript.tree;

import static org.snapscript.core.Reserved.TYPE_THIS;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.define.Instance;
import org.snapscript.core.function.Function;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.parse.StringToken;

public class Super implements Evaluation {

   private final StringToken token;
   
   public Super(StringToken token) {
      this.token = token;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Module module = scope.getModule();
      Context context = module.getContext();
      ThreadStack stack = context.getStack();
      Function function = stack.current(); // we can determine the function type
      
      if(function == null) {
         throw new InternalStateException("No enclosing function for 'super' reference");
      }
      State state = scope.getState();
      Value value = state.get(TYPE_THIS);
      
      if(value == null) {
         throw new InternalStateException("No enclosing type for 'super' reference");
      }
      Instance instance = value.getValue();
      Instance base = resolve(instance, function);
      
      if(base == null) {
         throw new InternalStateException("Illegal reference to 'super'"); // closure?
      }
      return ValueType.getTransient(base);
   }  
   
   private Instance resolve(Instance instance, Function function) {
      Type location = function.getType();
      Instance next = instance;
      
      while(next != null) {
         Type actual = next.getHandle();
         
         if(location == actual){
            return next.getSuper(); // return the object instance for super
         }
         next = next.getSuper(); 
      }
      return null;
   }

}
