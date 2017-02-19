/*
 * MethodMatcher.java December 2016
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

import java.lang.reflect.Method;

import org.snapscript.core.ModifierType;

public class MethodMatcher {

   private final ModifierConverter converter;
   
   public MethodMatcher() {
      this.converter = new ModifierConverter();
   }
   
   public Method match(Method[] methods, Class require, String name) throws Exception {
      PropertyType[] types = PropertyType.values();

      for(Method method : methods) {         
         int modifiers = converter.convert(method);
         
         if(!ModifierType.isStatic(modifiers) && ModifierType.isPublic(modifiers)) {
            for(PropertyType type : types) {            
               if(type.isWrite(method)) {
                  Class[] parameters = method.getParameterTypes();
                  Class actual = parameters[0];
                  
                  if(actual == require) {
                     String property = type.getProperty(method);
      
                     if(property.equals(name)) {
                        return method;
                     }
                  }
               }
            }
         }
      }
      return null;
   }
}
