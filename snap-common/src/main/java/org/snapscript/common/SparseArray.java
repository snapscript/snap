/*
 * SparseArray.java December 2016
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

package org.snapscript.common;

public class SparseArray<T> {

   private volatile Object[][] segments;
   private volatile int capacity;
   private volatile int block;

   public SparseArray(int length) {
      this(length, 32); // compute better default distribution
   }
   
   public SparseArray(int length, int block) {
      this.segments = new Object[length / block + 1][];
      this.capacity = length;
      this.block = block;
   }
   
   public T set(int index, T value) {
      int offset = index % block;
      Object[] segment = segment(index);
      Object result = segment[offset];
      
      segment[offset] = value;
      return (T)result;
   }
   
   public T get(int index) {
      int section = index /block;
      
      if(section < segments.length) {
         Object[] segment = segments[section];
         
         if(segment != null) {
            return (T)segment[index % block];
         }
      }
      return null;
   }
   
   public T remove(int index) {
      int section = index /block;
      
      if(section < segments.length) {
         Object[] segment = segments[section];
         
         if(segment != null) {
            int offset = index % block;
            Object result = segment[offset];
           
            segment[offset] = null;
            return (T)result;
         }
      }
      return null;
   }
   
   protected Object[] segment(int index) {
      int section = index /block;
      
      if(index >= capacity) {
         expand(capacity * 2 < index ? index : capacity *2);
      }
      Object[] segment = segments[section];
      
      if(segment == null) {
         segments[section] = new Object[block];
      }
      return segments[section];
   }
   
   protected void expand(int length) {
      Object[][] copy = new Object[length / block + 1][];

      for(int i = 0; i < segments.length; i++) {
         copy[i] = segments[i];
      }
      capacity = length;
      segments = copy;
   }
   
   public int length() {
      return capacity;
   }
}

