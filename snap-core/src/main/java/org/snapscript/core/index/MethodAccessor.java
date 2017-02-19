/*
 * MethodAccessor.java December 2016
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

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Type;
import org.snapscript.core.function.Accessor;

public class MethodAccessor implements Accessor<Object> {

   private final ParameterConverter converter;
   private final Method write;
   private final Method read;

   public MethodAccessor(Type type, Method read){
      this(type, read, null);
   }
   
   public MethodAccessor(Type type, Method read, Method write){
      this.converter = new ParameterConverter(type);
      this.write = write;
      this.read = read;
   }
   
   @Override
   public Object getValue(Object source) {
      try {
         return read.invoke(source);
      } catch(Exception e) {
         throw new InternalStateException("Illegal access to " + read, e);
      }
   }

   @Override
   public void setValue(Object source, Object value) {
      try {
         if(write == null) {
            throw new InternalStateException("Illegal write for " + read);
         }
         if(value != null){
            value = converter.convert(value);
         }
         write.invoke(source, value);
      } catch(Exception e) {
         throw new InternalStateException("Illegal access to " + write, e);
      }
   }
   
   private static class ParameterConverter {
      
      private final Type type;
      
      public ParameterConverter(Type type) {
         this.type = type;
      }
      
      public Object convert(Object value) {
         Class actual = value.getClass();
         Class parent = actual.getSuperclass();
         Class require = type.getType();
         
         if(require == String.class) {
            return String.valueOf(value);
         }
         if(parent == Number.class) {
            Number number = (Number)value;
            
            if(require == Double.class) {
               return number.doubleValue();
            }
            if(require == Integer.class) {
               return number.intValue();
            }
            if(require == Float.class) {
               return number.floatValue();
            }
            if(require == Long.class) {
               return number.longValue();
            }
            if(require == Short.class) {
               return number.shortValue();
            }
            if(require == Byte.class) {
               return number.byteValue();
            }
         }
         return value;
      }
   }
}
