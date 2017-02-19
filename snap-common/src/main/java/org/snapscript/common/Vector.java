/*
 * Vector.java December 2016
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

import java.util.Iterator;

public class Vector<T> implements Iterable<T> {

   private Node<T> first;
   private Node<T> last;
   private int size;
   
   public Vector() {
      super();
   }
   
   @Override
   public Iterator<T> iterator() {
      return new NodeIterator<T>(first);
   }
   
   public T first() {
      if(first != null) {
         return first.value;
      }
      return null;
   }
   
   public T last() {
      if(last != null) {
         return last.value;
      }
      return null;
   }
   
   public T get(int index) {
      Node<T> next = first;
      
      for(int i = 0; i< index; i++) {
         if(next == null) {
            return null;
         }
         next = next.next;
      }
      return next.value;
   }
   
   public void add(T value) {
      Node<T> node = new Node(null, value);
      
      if(first == null) {
         first = node;
         last =node;
      } else {
         last.next = node;
         last = node;
      }
      size++;
   }
   
   public int size() {
      return size;
   }
   
   private static class NodeIterator<T> implements Iterator<T> {

      private Node<T> next;
      
      public NodeIterator(Node<T> next) {
         this.next = next;
      }
      
      @Override
      public boolean hasNext() {
         return next != null;
      }

      @Override
      public T next() {
         T value = null;
         
         if(next != null) {
            value = next.value;
            next = next.next;
         }
         return value;
      }
      
      @Override
      public void remove() {
         throw new UnsupportedOperationException("Remove not supported");
      }
   }
   
   private static class Node<T> {
      
      private Node<T> next;
      private T value;
      
      public Node(Node<T> next, T value) {
         this.value = value;
         this.next = next;
      }
   }
}
