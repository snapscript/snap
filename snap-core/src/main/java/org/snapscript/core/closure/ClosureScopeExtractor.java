/*
 * ClosureScopeExtractor.java December 2016
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

import org.snapscript.core.Model;
import org.snapscript.core.Scope;
import org.snapscript.core.State;

public class ClosureScopeExtractor {

   public ClosureScopeExtractor() {
      super();
   }
   
   public Scope extract(Scope scope) {
      Model model = scope.getModel();
      State state = scope.getState();
      Iterator<String> names = state.iterator();
      
      if(names.hasNext()) {
         Scope capture = new ClosureScope(model, scope);
         
         while(names.hasNext()) {
            String name = names.next();
            State inner = capture.getState();
            
            inner.get(name); // pull from outer scope
         }
         return capture;
      }
      return scope;
   }
}
