/*
 * AnyFunctionBuilder.java December 2016
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

package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.PUBLIC;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.ParameterBuilder;
import org.snapscript.core.function.Signature;

public class AnyFunctionBuilder {
   
   private final AnyInvocationGenerator generator;
   private final ParameterBuilder builder;
   
   public AnyFunctionBuilder() {
      this.generator = new AnyInvocationGenerator();
      this.builder = new ParameterBuilder();
   }

   public Function create(Type type, String name, Class invoke, Class... types) throws Exception {
      Module module = type.getModule();
      Context context = module.getContext();
      TypeLoader loader = context.getLoader();
      Invocation invocation = (Invocation)generator.create(invoke);
      
      if(invocation == null) {
         throw new InternalStateException("Could not create invocation for " + invoke);
      }
      List<Parameter> parameters = new ArrayList<Parameter>();
      Signature signature = new Signature(parameters, module);
      
      for(int i = 0; i < types.length; i++){
         Class require = types[i];
         Type constraint = loader.loadType(require);
         Parameter parameter = null;
         
         if(require == Object.class) { // avoid proxy wrapping
            parameter = builder.create(null, i);
         } else {
            parameter = builder.create(constraint, i);
         }
         parameters.add(parameter);
      }
      return new InvocationFunction<Object>(signature, invocation, type, null, name, PUBLIC.mask);
   }
}
