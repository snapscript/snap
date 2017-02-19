/*
 * ArrayWrapper.java December 2016
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

import java.util.AbstractList;
import java.util.RandomAccess;

import org.snapscript.core.InternalArgumentException;

public abstract class ArrayWrapper<T> extends AbstractList<T> implements RandomAccess {

   public int length() {
      return size();
   }
   
   @Override
   public boolean add(T element) {
      throw new InternalArgumentException("Array cannot be resized");
   }
   
   @Override
   public void add(int index, T element) {
      throw new InternalArgumentException("Array cannot be resized");
   }

   @Override
   public boolean contains(Object o) {
      int index = indexOf(o);

      if (index == -1) {
         return false;
      }
      return true;

   }
}
