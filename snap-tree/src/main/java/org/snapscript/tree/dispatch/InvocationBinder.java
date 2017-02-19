/*
 * InvocationBinder.java December 2016
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

package org.snapscript.tree.dispatch;

import java.util.Map;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.function.Function;

public class InvocationBinder {
   
   public InvocationBinder() {
      super();
   }

   public InvocationDispatcher bind(Scope scope, Object left) {
      if(left != null) {
         Class type = left.getClass();
         
         if(Scope.class.isInstance(left)) {
            return new ScopeDispatcher(scope, left);            
         }
         if(Module.class.isInstance(left)) {
            return new ModuleDispatcher(scope, left);
         }  
         if(Type.class.isInstance(left)) {
            return new TypeDispatcher(scope, left);
         }  
         if(Map.class.isInstance(left)) {
            return new MapDispatcher(scope, left);
         }
         if(Function.class.isInstance(left)) {
            return new FunctionDispatcher(scope, left);
         }
         if(type.isArray()) {
            return new ArrayDispatcher(scope, left);
         }
         return new ObjectDispatcher(scope, left);
      }
      Type type = scope.getType();
      
      if(type != null) {
         return new ScopeDispatcher(scope, scope);
      }
      return new LocalDispatcher(scope);      
   }
}