/*
 * StaticAccessor.java December 2016
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
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.define.Initializer;

public class StaticAccessor implements Accessor {

   private final Initializer initializer;
   private final Accessor accessor;
   private final Scope scope;
   private final String name;
   private final Type type;
   
   public StaticAccessor(Initializer initializer, Scope scope, Type type, String name) {
      this.accessor = new ScopeAccessor(name);
      this.initializer = initializer;
      this.scope = scope;
      this.name = name;
      this.type = type;
   }
   
   @Override
   public Object getValue(Object source) {
      try {
         State state = scope.getState();
         Value field = state.get(name);
         
         if(field == null) {
            initializer.compile(scope, type);           
         }
      }catch(Exception e){
         throw new InternalStateException("Static reference of '" + name + "' in '" + type + "' failed", e);
      }
      return accessor.getValue(scope);
   }

   @Override
   public void setValue(Object source, Object value) {
      try {
         State state = scope.getState();
         Value field = state.get(name);
         
         if(field == null) {
            initializer.compile(scope, type);           
         }    
      }catch(Exception e){
         throw new InternalStateException("Static reference of '" + name + "' in '" + type + "' failed", e);
      }   
      accessor.setValue(scope,value);
   }

}
