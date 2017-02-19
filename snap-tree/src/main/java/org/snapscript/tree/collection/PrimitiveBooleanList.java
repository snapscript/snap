/*
 * PrimitiveBooleanList.java December 2016
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

public class PrimitiveBooleanList extends ArrayWrapper<Boolean> {

   private final boolean[] array;
   private final int length;

   public PrimitiveBooleanList(boolean[] array) {
      this.length = array.length;
      this.array = array;
   }

   @Override
   public int size() {
      return length;
   }
   
   @Override
   public Boolean get(int index) {
      return array[index];
   }

   @Override
   public Boolean set(int index, Boolean value) {
      Boolean result = array[index];
      array[index] = value;
      return result;
   }
   
   @Override
   public Object[] toArray() {
      Object[] copy = new Boolean[length];
      
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
         Boolean flag = array[i];
         Object value = flag;
         
         if(type == String[].class) {
            value = value.toString();
         } else if(type == Boolean[].class) {
            value = flag;
         } else if(type == Object[].class) {
            value = flag;
         } else {
            throw new InternalArgumentException("Incompatible array type");
         }
         copy[i] = (T)value;
      }
      return copy;
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