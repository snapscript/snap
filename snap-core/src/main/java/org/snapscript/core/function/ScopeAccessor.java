/*
 * ScopeAccessor.java December 2016
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

package org.snapscript.core.function;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;

public class ScopeAccessor implements Accessor<Scope> {

   private final String name;
   
   public ScopeAccessor(String name) {
      this.name = name;
   }
   
   @Override
   public Object getValue(Scope source) {
      State state = source.getState();
      Value field = state.get(name);
      
      if(field == null){
         throw new InternalStateException("Field '" + name + "' does not exist");
      }
      return field.getValue();
   }

   @Override
   public void setValue(Scope source, Object value) {
      State state = source.getState();
      Value field = state.get(name);
      
      if(field == null){
         throw new InternalStateException("Field '" + name + "' does not exist");
      }
      field.setValue(value);
   }

}
