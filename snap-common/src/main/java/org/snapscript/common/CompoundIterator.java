/*
 * CompoundIterator.java December 2016
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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CompoundIterator<T> implements Iterator<T>{

   private Iterator[] iterators;
   private Object next;
   private Set done;
   private int index;
   
   public CompoundIterator(Iterator... iterators) {
      this.done = new HashSet<T>();
      this.iterators = iterators;
   }

   @Override
   public boolean hasNext() {
      if(next == null) {
         while(index < iterators.length) {
            Iterator iterator = iterators[index];
            
            while(iterator.hasNext()) {
               Object value = iterator.next();
               
               if(done.add(value)) {
                  next = value;
                  return true;
               }
            }
            index++;
         }
      }
      return next != null;
   }

   @Override
   public T next() {
      Object local = next;
      
      if(local == null) {
         if(!hasNext()) {
            return null;
         }
         local = next;
      }
      next = null;
      return (T)local;
   }
   
   @Override
   public void remove() {
      throw new UnsupportedOperationException("Remove not supported");
   }
}
