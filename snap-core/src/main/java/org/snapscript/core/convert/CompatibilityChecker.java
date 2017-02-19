/*
 * CompatibilityChecker.java December 2016
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

package org.snapscript.core.convert;

import static org.snapscript.core.convert.Score.INVALID;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;

public class CompatibilityChecker {
   
   public CompatibilityChecker() {
      super();
   }

   public boolean compatible(Scope scope, Object value, String name) throws Exception {
      if(name != null) {
         Module module = scope.getModule();
         Type type = module.getType(name);

         return compatible(scope, value, type);
      }
      return true;
   }

   public boolean compatible(Scope scope, Object value, Type type) throws Exception {
      if(type != null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         ConstraintMatcher matcher = context.getMatcher();
         ConstraintConverter converter = matcher.match(type);
         Score score = converter.score(value);
         
         return score.compareTo(INVALID) > 0;
      }
      return true;
   }
}
