/*
 * ParameterExtractor.java December 2016
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

package org.snapscript.tree.function;

import java.util.List;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.convert.CompatibilityChecker;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;

public class ParameterExtractor {
   
   private final CompatibilityChecker checker;
   private final Signature signature;
   
   public ParameterExtractor(Signature signature) {
      this.checker = new CompatibilityChecker();
      this.signature = signature;
   }

   public void extract(Scope scope, Object... arguments) throws Exception {
      List<Parameter> parameters = signature.getParameters();
      State state = scope.getState();
      
      for(int i = 0; i < arguments.length; i++) {
         Parameter parameter = parameters.get(i);
         String name = parameter.getName();
         Object argument = arguments[i];
         Value value = create(scope, argument, i);
         
         state.add(name, value);
      }
   }

   private Value create(Scope scope, Object value, int index) throws Exception {
      List<Parameter> parameters = signature.getParameters();
      Parameter parameter = parameters.get(index);
      Type type = parameter.getType();
      String name = parameter.getName();
      int length = parameters.size();
      
      if(index >= length -1) {
         if(signature.isVariable()) {
            Object[] list = (Object[])value;
            
            for(int i = 0; i < list.length; i++) {
               Object entry = list[i];
               
               if(!checker.compatible(scope, entry, type)) {
                  throw new InternalStateException("Parameter '" + name + "...' does not match constraint '" + type + "'");
               }
            }
            return ValueType.getReference(value, type);
         }
      }
      if(!checker.compatible(scope, value, type)) {
         throw new InternalStateException("Parameter '" + name + "' does not match constraint '" + type + "'");
      }
      return ValueType.getReference(value, type);         
   }
}
