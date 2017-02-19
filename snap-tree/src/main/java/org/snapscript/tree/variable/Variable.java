/*
 * Variable.java December 2016
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

package org.snapscript.tree.variable;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;
import org.snapscript.tree.NameExtractor;

public class Variable implements Evaluation {
   
   private final VariableResolver resolver;
   private final NameExtractor extractor;
   
   public Variable(Evaluation identifier) {
      this.extractor = new NameExtractor(identifier);
      this.resolver = new VariableResolver();
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception{
      String name = extractor.extract(scope);
      
      if(left == null) {
         State state = scope.getState();
         Value value = state.get(name);
         
         if(value != null) { 
            return value;
         }
      }
      return resolve(scope, left, name);
   }  
   
   private Value resolve(Scope scope, Object left, String name) throws Exception {
      Value value = resolver.resolve(scope, left, name);
      
      if(value == null) {
         throw new InternalStateException("Could not resolve '" + name +"' in scope");
      }
      return value;
   }
}