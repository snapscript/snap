/*
 * ModifierConverter.java December 2016
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

import static org.snapscript.core.ModifierType.ABSTRACT;
import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.ModifierType.PRIVATE;
import static org.snapscript.core.ModifierType.PUBLIC;
import static org.snapscript.core.ModifierType.STATIC;
import static org.snapscript.core.ModifierType.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ModifierConverter {
   
   public int convert(Method method) {
      int result = PROTECTED.mask;
      
      if(method != null) {
         int mask = method.getModifiers();
   
         if(method.isVarArgs()) {
            result |= VARARGS.mask;
         }
         if(Modifier.isAbstract(mask)) {
            result |= ABSTRACT.mask;
         }
         if(Modifier.isFinal(mask)) {
            result |= CONSTANT.mask;
         }
         if(Modifier.isPrivate(mask)) {
            result |= PRIVATE.mask;
            result &= ~PROTECTED.mask;
         }
         if(Modifier.isPublic(mask)) {
            result |= PUBLIC.mask;
            result &= ~PROTECTED.mask;
         }
         if(Modifier.isStatic(mask)) {
            result |= STATIC.mask;
         }
      }
      return result;
   }
   
   public int convert(Constructor constructor) {
      int result = PROTECTED.mask;
      
      if(constructor != null) {
         int modifiers = constructor.getModifiers();
   
         if(constructor.isVarArgs()) {
            result |= VARARGS.mask;
         }
         if(Modifier.isAbstract(modifiers)) {
            result |= ABSTRACT.mask;
         }
         if(Modifier.isFinal(modifiers)) {
            result |= CONSTANT.mask;
         }
         if(Modifier.isPrivate(modifiers)) {
            result |= PRIVATE.mask;
            result &= ~PROTECTED.mask;
         }
         if(Modifier.isPublic(modifiers)) {
            result |= PUBLIC.mask;
            result &= ~PROTECTED.mask;
         }
         result |= STATIC.mask;
      }
      return result;
   }
   
   public int convert(Field field) {
      int result = PROTECTED.mask; // default is protected
      
      if(field != null) {
         int modifiers = field.getModifiers();
         
         if(Modifier.isFinal(modifiers)) {
            result |= CONSTANT.mask;
         }
         if(Modifier.isPrivate(modifiers)) {
            result |= PRIVATE.mask;
            result &= ~PROTECTED.mask;
         }
         if(Modifier.isPublic(modifiers)) {
            result |= PUBLIC.mask;
            result &= ~PROTECTED.mask;
         }
         if(Modifier.isStatic(modifiers)) {
            result |= STATIC.mask;
         }
      }
      return result;
   }
}
