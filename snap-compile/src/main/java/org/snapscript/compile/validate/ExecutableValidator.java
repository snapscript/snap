/*
 * ExecutableValidator.java December 2016
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

package org.snapscript.compile.validate;

import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.ProgramValidator;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.convert.ConstraintMatcher;

public class ExecutableValidator implements ProgramValidator {

   private final ModuleValidator validator;
   
   public ExecutableValidator(ConstraintMatcher matcher, TypeExtractor extractor) {
      this.validator = new ModuleValidator(matcher, extractor);
   }
   
   @Override
   public void validate(Context context) throws Exception {
      ModuleRegistry registry = context.getRegistry();
      List<Module> modules = registry.getModules();
      
      for(Module module : modules) {
         validator.validate(module);
      }
   }
}
