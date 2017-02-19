/*
 * StringConverter.java December 2016
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
import static org.snapscript.core.convert.Score.POSSIBLE;

import org.snapscript.core.Type;

public class StringConverter extends ConstraintConverter {
   
   @Override
   public Score score(Type actual) throws Exception {
      if(actual != null) {
         Class real = actual.getType();
         
         if(real == String.class) {
            return EXACT;
         }
      }
      return POSSIBLE;
   }
   
   @Override
   public Score score(Object value) throws Exception {
      if(value != null) {
         Class type = value.getClass();
      
         if(type == String.class) {
            return EXACT;
         }
      }
      return POSSIBLE;
   }
   
   @Override
   public Object convert(Object object) throws Exception {
      if(object != null) {
         return String.valueOf(object);
      }
      return null;
   }
}