/*
 * ByteList.java December 2016
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

package org.snapscript.tree.collection;

import org.snapscript.core.InternalArgumentException;

public class ByteList extends ArrayWrapper<Object> {

   private final Byte[] array;
   private final int length;
   
   public ByteList(Byte[] array) {
      this.length = array.length;
      this.array = array;
   }

   @Override
   public int size() {
      return length;
   }

   @Override
   public Object get(int index) {
      return array[index];
   }

   @Override
   public Object set(int index, Object value) {
      Byte previous = array[index];
      Class type = value.getClass();
      
      if(type == String.class) {
         String text = (String)value;
         array[index] = Byte.parseByte(text);
      } else {
         Number number = (Number)value;
         array[index] = number.byteValue();
      }
      return previous;
   }
   
   @Override
   public Object[] toArray() {
      Object[] copy = new Byte[length];
      
      for(int i = 0; i < length; i++) {
         copy[i] = array[i];
      }
      return copy;
   }

   @Override
   public <T> T[] toArray(T[] copy) {
      Class type = copy.getClass();
      int require = copy.length;
     
      for(int i = 0; i < length && i < require; i++) {
         Byte number = array[i];
         Object value = number;
         
         if(type != Byte[].class) {
            if(type == Short[].class) {
               value = number.shortValue();
            } else if(type == Double[].class) {
               value = number.doubleValue();
            } else if(type == Float[].class) {
               value = number.floatValue();
            } else if(type == Long[].class) {
               value = number.longValue();
            } else if(type == Integer[].class) {
               value = number.intValue();
            } else if(type == String[].class) {
               value = number.toString();
            } else if(type == Object[].class) {
               value = number;
            } else {
               throw new InternalArgumentException("Incompatible array type");
            }
         }
         copy[i] = (T)value;
      }
      return copy;
   }

   @Override
   public int indexOf(Object object) {
      Class type = object.getClass();
      
      for (int i = 0; i < length; i++) {
         Byte number = array[i];
         Object value = number;
         
         if(type != Byte.class) {
            if(type == Float.class) {
               value = number.floatValue();
            } else if(type == Short.class) {
               value = number.shortValue();
            } else if(type == Double.class) {
               value = number.doubleValue();
            } else if(type == Long.class) {
               value = number.longValue();
            } else if(type == Integer.class) {
               value = number.intValue();
            } else if(type == String.class) {
               value = number.toString();
            } else {
               throw new InternalArgumentException("Incompatible value type");
            }
         }
         if (object.equals(value)) {
            return i;
         }
      }
      return -1;
   }
}
