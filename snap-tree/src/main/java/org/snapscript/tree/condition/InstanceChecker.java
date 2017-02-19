/*
 * InstanceChecker.java December 2016
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

package org.snapscript.tree.condition;

import java.util.Set;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;

public class InstanceChecker {
   
   public InstanceChecker() {
     super();
   }
   
   public boolean instanceOf(Scope scope, Object left, Object right) {
      if(right != null && left != null) {
         try {
            Module module = scope.getModule();
            Context context = module.getContext();
            TypeExtractor extractor = context.getExtractor();
            Type actual = extractor.getType(left);
            Set<Type> types = extractor.getTypes(actual);
            Type require = (Type)right;
            
            return types.contains(require);
         } catch(Exception e) {
            return false;
         }
      }
      return false;
   }

}
