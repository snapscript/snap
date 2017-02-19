/*
 * FunctionAccessor.java December 2016
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

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.function.Accessor;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;

public class FunctionAccessor implements Accessor<Scope> {

   private final Function function;
   
   public FunctionAccessor(Function function) {
      this.function = function;
   }
   
   @Override
   public Object getValue(Scope source) {
      try {
         Invocation invocation = function.getInvocation();
         Result result = invocation.invoke(source, source);
         
         return result.getValue();
      } catch(Exception e) {
         throw new InternalStateException("Illegal read access to " + function, e);
      }
   }

   @Override
   public void setValue(Scope source, Object value) {
      throw new InternalStateException("Illegal write access to " + function);
   }

}
