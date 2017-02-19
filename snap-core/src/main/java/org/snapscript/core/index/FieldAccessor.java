/*
 * FieldAccessor.java December 2016
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

import java.lang.reflect.Field;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.function.Accessor;

public class FieldAccessor implements Accessor<Object>{

   private final Field field;
   
   public FieldAccessor(Field field){
      this.field = field;
   }
   
   @Override
   public Object getValue(Object source) {
      try {
         return field.get(source);
      } catch(Exception e) {
         throw new InternalStateException("Illegal access to " + field, e);
      }
   }

   @Override
   public void setValue(Object source, Object value) {
      try {
         field.set(source, value);
      } catch(Exception e) {
         throw new InternalStateException("Illegal access to " + field, e);
      }
   }

}
