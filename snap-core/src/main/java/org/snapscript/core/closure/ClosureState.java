/*
 * ClosureState.java December 2016
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

package org.snapscript.core.closure;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.snapscript.common.CompoundIterator;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;

public class ClosureState implements State {
   
   private final Map<String, Value> values;
   private final Scope scope;

   public ClosureState(Scope scope) {
      this.values = new ConcurrentHashMap<String, Value>();
      this.scope = scope;
   }
   
   @Override
   public Iterator<String> iterator() {
      Set<String> keys = values.keySet();
      Iterator<String> inner = keys.iterator();
      
      if(scope != null) {
         State state = scope.getState();
         Iterator<String> outer = state.iterator();
         
         return new CompoundIterator<String>(inner, outer);
      }
      return inner;
   }

   @Override
   public Value get(String name) {
      Value value = values.get(name);
      
      if(value == null) {
         State state = scope.getState();
         
         if(state == null) {
            throw new InternalStateException("Scope for '" + name + "' does not exist");
         }
         value = state.get(name);
         
         if(value != null) {
            if(!value.isProperty()) { // this does not really work
               Object object = value.getValue();
               Value constant = ValueType.getConstant(object);
               
               values.put(name, constant); // cache as constant
            }
         }
      }
      return value;
   }
   
   @Override
   public void add(String name, Value value) {
      Value variable = values.get(name);

      if(variable != null) {
         throw new InternalStateException("Variable '" + name + "' already exists");
      }
      values.put(name, value);      
   }
   
   @Override
   public String toString() {
      return String.valueOf(values);
   }
}
