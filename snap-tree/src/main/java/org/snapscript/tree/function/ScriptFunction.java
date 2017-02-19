/*
 * ScriptFunction.java December 2016
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

import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.NameExtractor;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.constraint.ConstraintExtractor;

public class ScriptFunction extends Statement {
   
   private final ConstraintExtractor constraint;
   private final ParameterList parameters;
   private final FunctionBuilder builder;
   private final NameExtractor extractor;
   private final Statement body;
   
   public ScriptFunction(Evaluation identifier, ParameterList parameters, Statement body){  
      this(identifier, parameters, null, body);
   }
   
   public ScriptFunction(Evaluation identifier, ParameterList parameters, Constraint constraint, Statement body){  
      this.constraint = new ConstraintExtractor(constraint);
      this.extractor = new NameExtractor(identifier);
      this.builder = new FunctionBuilder(body);
      this.parameters = parameters;
      this.body = body;
   }  
   
   @Override
   public Result compile(Scope scope) throws Exception {
      Module module = scope.getModule();
      List<Function> functions = module.getFunctions();
      Signature signature = parameters.create(scope);
      String name = extractor.extract(scope);
      Type returns = constraint.extract(scope);
      Function function = builder.create(signature, module, returns, name);
      
      functions.add(function);
      body.compile(scope);
      
      return ResultType.getNormal(function);
   }
}