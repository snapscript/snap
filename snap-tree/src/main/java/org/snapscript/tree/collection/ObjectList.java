/*
 * ObjectList.java December 2016
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

import java.lang.reflect.Array;

public class ObjectList extends ArrayWrapper<Object> {

   private final Object[] array;
   private final Class type;
   private final int length;

   public ObjectList(Object[] array, Class type) {
      this.length = array.length;
      this.array = array;
      this.type = type;
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
      Object previous = array[index];
      array[index] = value;
      return previous;
   }

   @Override
   public Object[] toArray() {
      Object instance = Array.newInstance(type, length);
      Object[] copy = (Object[])instance;
      
      for(int i = 0; i < length; i++) {
         copy[i] = array[i];
      }
      return copy;
   }

   @Override
   public <T> T[] toArray(T[] copy) {
      Class type = copy.getClass();
      int require = copy.length;
     
      if(require >= length) {
         for(int i = 0; i < length; i++) {
            copy[i] = (T)array[i];
         }
      }
      return (T[])toArray();
   }

   @Override
   public int indexOf(Object object) {
      for (int i = 0; i < length; i++) {
         Object value = array[i];

         if (object.equals(value)) {
            return i;
         }
      }
      return -1;
   }
}

