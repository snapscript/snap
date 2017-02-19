/*
 * CharacterConverter.java December 2016
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

import static org.snapscript.core.convert.Score.EXACT;
import static org.snapscript.core.convert.Score.INVALID;
import static org.snapscript.core.convert.Score.POSSIBLE;

import org.snapscript.core.InternalArgumentException;
import org.snapscript.core.Type;

public class CharacterConverter extends ConstraintConverter {

   private final CharacterMatcher matcher;
   private final Type type;
   
   public CharacterConverter(Type type) {
      this.matcher = new CharacterMatcher();
      this.type = type;
   }
   
   @Override
   public Score score(Type actual) throws Exception {
      if(actual != null) {
         Class real = actual.getType();
         
         if(real != null) {
            if(real == Character.class) {
               return EXACT;
            }
            if(real == String.class) {
               return POSSIBLE;
            }
         }
         return INVALID;
      }
      return POSSIBLE;
   }
   
   @Override
   public Score score(Object value) throws Exception {
      Class require = type.getType();
      
      if(value != null) {
         Class actual = value.getClass();
         
         if(actual == Character.class) {
            return EXACT;
         }
         if(actual == String.class) {
            String text = String.valueOf(value);
            
            if(matcher.matchCharacter(text)) {
               return POSSIBLE;
            }
         }
         return INVALID;
      }
      if(require.isPrimitive()) {
         return INVALID;
      }
      return POSSIBLE;
   }
   
   @Override
   public Object convert(Object value) throws Exception {
      Class require = type.getType();
      
      if(value != null) {
         Class type = value.getClass();
         
         if(type == String.class) {
            return convert(require, (String)value);
         }
         if(type == Character.class) {
            return value;
         }
         throw new InternalArgumentException("Conversion from " + type + " to character is not possible");
      }
      if(require.isPrimitive()) {
         throw new InternalArgumentException("Invalid conversion from null to primitive character");
      }
      return null;
   }
}