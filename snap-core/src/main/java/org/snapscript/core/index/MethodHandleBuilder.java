/*
 * MethodHandleBuilder.java December 2016
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

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodHandleBuilder {

   private static final String ALLOWED_MODES = "allowedModes";
   
   private final Method method;

   public MethodHandleBuilder(Method method) {
      this.method = method;
   }
   
   public MethodHandle create() throws Exception {
      Class target = method.getDeclaringClass();
      Lookup lookup = MethodHandles.lookup();
      Lookup actual = lookup.in(target);
      Class access = lookup.getClass();
      Field modes = access.getDeclaredField(ALLOWED_MODES);
      
      modes.setAccessible(true);
      modes.set(actual, Modifier.PRIVATE);
      
      return actual.unreflectSpecial(method, target);
   }
}
