/*
 * ClosureParameterList.java December 2016
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

package org.snapscript.tree.closure;

import org.snapscript.core.Scope;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.function.ParameterDeclaration;
import org.snapscript.tree.function.ParameterList;

public class ClosureParameterList {
   
   private final ParameterList multiple;
   private final ParameterList single;
   
   public ClosureParameterList() {
      this(null, null);
   }
  
   public ClosureParameterList(ParameterList multiple) {
      this(multiple, null);
   }
   
   public ClosureParameterList(ParameterDeclaration single) {
      this(null, single);
   }
   
   public ClosureParameterList(ParameterList multiple, ParameterDeclaration single) {
      this.single = new ParameterList(single);
      this.multiple = multiple;
   }
   
   public Signature create(Scope scope) throws Exception{
      if(multiple != null) {
         return multiple.create(scope);
      }
      return single.create(scope);
   }
}
