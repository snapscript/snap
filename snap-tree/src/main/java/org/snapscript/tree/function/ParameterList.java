/*
 * ParameterList.java December 2016
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

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.ParameterBuilder;
import org.snapscript.core.function.Signature;

public class ParameterList {
   
   private VariableArgumentChecker checker;
   private ParameterDeclaration[] list;
   private ParameterBuilder builder;
   private Signature signature;
   
   public ParameterList(ParameterDeclaration... list) {
      this.checker = new VariableArgumentChecker(list);
      this.builder = new ParameterBuilder();
      this.list = list;
   }
   
   public Signature create(Scope scope) throws Exception{
      return create(scope, null);
   }
   
   public Signature create(Scope scope, String prefix) throws Exception{
      Module module = scope.getModule();
      
      if(signature == null) {
         List<Parameter> parameters = new ArrayList<Parameter>();
         
         if(prefix != null) {
            Context context = module.getContext();
            TypeLoader loader = context.getLoader();
            Type constraint = loader.loadType(Type.class);
            Parameter parameter = builder.create(constraint, prefix);
            
            parameters.add(parameter);
         }
         boolean variable = checker.isVariable(scope);
         
         for(int i = 0; i < list.length; i++) {
            ParameterDeclaration declaration = list[i];
            
            if(declaration != null) {
               Parameter parameter = declaration.get(scope);
               parameters.add(parameter);
            }
         }
         signature = new Signature(parameters, module, variable);
      }
      return signature;
   }
}