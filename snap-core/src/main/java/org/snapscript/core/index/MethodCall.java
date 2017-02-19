/*
 * MethodCall.java December 2016
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

package org.snapscript.core.index;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.snapscript.core.InternalStateException;

public class MethodCall {

   private final Method method;
   
   public MethodCall(Method method) {
      this.method = method;
   }
   
   public Object call(Object object, Object[] arguments) throws Exception {
      String name = method.getName();
      
      try {
         return method.invoke(object, arguments);
      }catch(InvocationTargetException cause) {
         Throwable target = cause.getTargetException();
         
         if(target != null) {
            throw new InternalStateException("Error occured invoking '" + name + "'", target);
         }
         throw cause;
      }
   }
}
