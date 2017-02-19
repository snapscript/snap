/*
 * OperationEvaluator.java December 2016
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

package org.snapscript.compile.assemble;

import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;

import java.util.concurrent.Executor;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.ExpressionEvaluator;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Model;
import org.snapscript.core.Scope;
import org.snapscript.core.ScopeMerger;
import org.snapscript.core.Value;

public class OperationEvaluator implements ExpressionEvaluator {
   
   private final EvaluationBuilder builder;
   private final ScopeMerger merger;
   private final Assembler assembler;
   
   public OperationEvaluator(Context context, Executor executor){
      this(context, executor, 200);
   }
   
   public OperationEvaluator(Context context, Executor executor, int limit) {
      this.assembler = new OperationAssembler(context);
      this.builder = new EvaluationBuilder(assembler, executor, limit);
      this.merger = new ScopeMerger(context);
   }
   
   @Override
   public <T> T evaluate(Model model, String source) throws Exception{
      return evaluate(model, source, DEFAULT_PACKAGE);
   }
   
   @Override
   public <T> T evaluate(Model model, String source, String module) throws Exception{
      Scope scope = merger.merge(model, module);
      return evaluate(scope, source, module);
   }
   
   @Override
   public <T> T evaluate(Scope scope, String source) throws Exception{
      return evaluate(scope, source, DEFAULT_PACKAGE);
   }
   
   @Override
   public <T> T evaluate(Scope scope, String source, String module) throws Exception{ 
      try {
         Evaluation evaluation = builder.create(source, module);
         Value reference = evaluation.evaluate(scope,null);
         
         return (T)reference.getValue();
      } catch(Exception e) {
         throw new InternalStateException("Could not evaluate '" + source + "'", e);
      }
   }
}
