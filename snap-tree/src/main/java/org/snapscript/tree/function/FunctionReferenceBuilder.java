/*
 * FunctionReferenceBuilder.java December 2016
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

import static org.snapscript.core.ModifierType.PUBLIC;
import static org.snapscript.core.Reserved.DEFAULT_PARAMETER;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Module;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;

public class FunctionReferenceBuilder {
   
   private final Parameter parameter;
   
   public FunctionReferenceBuilder() {
      this.parameter = new Parameter(DEFAULT_PARAMETER, null, true);
   }
   
   public Function create(Module module, Object value, String method) throws Exception {
      List<Parameter> parameters = new ArrayList<Parameter>();
      Signature signature = new Signature(parameters, module, true);
      Invocation invocation = new FunctionReferenceInvocation(module, value, method);
      
      parameters.add(parameter);
      
      return new InvocationFunction(signature, invocation, null, null, method, PUBLIC.mask);
   }
}