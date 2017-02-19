/*
 * FunctionReferenceAligner.java December 2016
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

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import org.snapscript.core.index.ScopeType;

public class FunctionReferenceAligner {
   
   private final String method;
   private final Object value;

   public FunctionReferenceAligner(Object value, String method){
      this.method = method;
      this.value = value;
   }
   
   public Object[] align(Object... list) throws Exception {      
      if(method.equals(TYPE_CONSTRUCTOR)) {
         if(ScopeType.class.isInstance(value)) { // inject type parameter
            Object[] arguments = new Object[list.length +1];
         
            for(int i = 0; i < list.length; i++) {
               arguments[i + 1] = list[i];
            }
            arguments[0] = value;
            return arguments;
         }
      }
      return list;
   }
}
