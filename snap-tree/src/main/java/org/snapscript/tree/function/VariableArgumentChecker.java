/*
 * VariableArgumentChecker.java December 2016
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

import org.snapscript.core.Scope;
import org.snapscript.core.function.Parameter;

public class VariableArgumentChecker {

   private ParameterDeclaration[] list;
   
   public VariableArgumentChecker(ParameterDeclaration... list) {
      this.list = list;
   }
   
   public boolean isVariable(Scope scope) throws Exception {
      int length = list.length;
      
      for(int i = 0; i < length - 1; i++) {
         ParameterDeclaration declaration = list[i];
         
         if(declaration != null) {
            Parameter parameter = declaration.get(scope);
            String name = parameter.getName();
         
            if(parameter.isVariable()) {
               throw new IllegalStateException("Illegal declaration " + name + "... at index " + i);
            }
         }
         
      }
      if(length > 0) {
         ParameterDeclaration declaration = list[length-1];
         
         if(declaration != null) {
            Parameter parameter = declaration.get(scope);
            
            if(parameter.isVariable()) {
               return true;
            }
         }
      }
      return false;
   }
}
