/*
 * CatchBlockList.java December 2016
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

import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.convert.CompatibilityChecker;
import org.snapscript.core.error.ErrorCauseExtractor;
import org.snapscript.core.function.Parameter;
import org.snapscript.tree.function.ParameterDeclaration;

public class CatchBlockList {
   
   private final ErrorCauseExtractor extractor;
   private final CompatibilityChecker checker;
   private final CatchBlock[] blocks;
   
   public CatchBlockList(CatchBlock... blocks) {
      this.extractor = new ErrorCauseExtractor();
      this.checker = new CompatibilityChecker();
      this.blocks = blocks;
   }    
   
   public Result compile(Scope scope) throws Exception {  
      for(CatchBlock block : blocks) {
         Statement statement = block.getStatement();
         
         if(statement != null) {
            statement.compile(scope);
         }
      }
      return ResultType.getNormal();
   }

   public Result execute(Scope scope, Result result) throws Exception {
      Object data = result.getValue();
      
      for(CatchBlock block : blocks) {
         ParameterDeclaration declaration = block.getDeclaration();
         Statement statement = block.getStatement();
         Parameter parameter = declaration.get(scope);
         Type type = parameter.getType();
         String name = parameter.getName();

         if(data != null) {
            Object cause = extractor.extract(scope, data);
            
            if(checker.compatible(scope, cause, type)) {
               Scope compound = scope.getInner();
               State state = compound.getState();
               Value constant = ValueType.getConstant(cause);
               
               state.add(name, constant);

               return statement.execute(compound);
            }
         }
      }
      return result;
   }
}
