/*
 * FunctionPointer.java December 2016
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

package org.snapscript.core.bind;

import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.Signature;
import org.snapscript.core.stack.ThreadStack;

public class FunctionPointer {
   
   private final ThreadStack stack;
   private final Function function;
   private final Object[] arguments;
   
   public FunctionPointer(Function function, ThreadStack stack, Object[] arguments) {
      this.arguments = arguments;
      this.function = function;
      this.stack = stack;
   }
   
   public Result call(Scope scope, Object object) throws Exception{
      Signature signature = function.getSignature();
      ArgumentConverter converter = signature.getConverter();
      Object[] list = converter.convert(arguments);
      Invocation invocation = function.getInvocation();
      Type type = function.getType();
      
      try {
         if(type != null) {
            stack.before(function);
         }
         return invocation.invoke(scope, object, list);
      } finally {
         if(type != null) {
            stack.after(function);
         }
      }
   }
}
