/*
 * ArgumentMatcher.java December 2016
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

package org.snapscript.core.function;

import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Type;
import org.snapscript.core.convert.ConstraintConverter;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.FixedArgumentConverter;
import org.snapscript.core.convert.NoArgumentConverter;
import org.snapscript.core.convert.VariableArgumentConverter;

public class ArgumentMatcher {

   private final Signature signature;
   private final Module module;
   
   public ArgumentMatcher(Signature signature, Module module) {
      this.signature = signature;
      this.module = module;
   }
   
   public ArgumentConverter getConverter() throws Exception {
      Context context = module.getContext();
      ConstraintMatcher matcher = context.getMatcher();
      List<Parameter> parameters = signature.getParameters();
      int size = parameters.size();
      
      if(size > 0) {
         ConstraintConverter[] converters = new ConstraintConverter[size];
         
         for(int i = 0; i < size - 1; i++) {
            Parameter parameter = parameters.get(i);
            Type type = parameter.getType();
            
            converters[i] = matcher.match(type);
         }
         Parameter parameter = parameters.get(size - 1);
         Type type = parameter.getType();
         
         if(type != null) {
            Type entry = type.getEntry();
            
            if(signature.isVariable()) {
               converters[size - 1] = matcher.match(entry);
            } else {
               converters[size - 1] = matcher.match(type);
            }
         } else {
            converters[size - 1] = matcher.match(type);
         }
         if(signature.isVariable()) {
            return new VariableArgumentConverter(converters);
         }
         return new FixedArgumentConverter(converters);
      }
      return new NoArgumentConverter();
   }
}
