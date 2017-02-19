/*
 * Range.java December 2016
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

import java.util.Iterator;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.tree.operation.SignedNumber;

public class Range implements Evaluation {

   private final SignedNumber start;
   private final SignedNumber finish;
   
   public Range(SignedNumber start, SignedNumber finish) {
      this.start = start;
      this.finish = finish;
   }

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Iterable<Number> range = create(scope, left);
      return ValueType.getTransient(range);
   }
   
   private Iterable<Number> create(Scope scope, Object left) throws Exception {
      Value first = start.evaluate(scope, left);
      Value last = finish.evaluate(scope, left);
      Long firstNumber = first.getLong();
      Long lastNumber = last.getLong();
      
      return new RangeIterable(firstNumber, lastNumber);
   }
   
   private static class RangeIterable implements Iterable<Number> {

      private final long first;
      private final long last;
      
      public RangeIterable(Long first, Long last) {
         this.first = first;
         this.last = last;
      }
      
      @Override
      public Iterator<Number> iterator() {
         if(first > last) {
            return new ReverseIterator(first, last);
         }
         return new ForwardIterator(first, last);
      }
   }
   
   private static class ForwardIterator implements Iterator<Number> {
      
      private long first;
      private long last;
      
      public ForwardIterator(Long first, Long last) {
         this.first = first;
         this.last = last;
      }

      @Override
      public boolean hasNext() {
         return first <= last;
      }

      @Override
      public Number next() {
         if(first <= last) {
            return first++;
         }
         return null;
      }
      
      @Override
      public void remove() {
         throw new UnsupportedOperationException("Illegal modification of range");
      }
   }
   
   private static class ReverseIterator implements Iterator<Number> {
      
      private long first;
      private long last;
      
      public ReverseIterator(Long first, Long last) {
         this.first = first;
         this.last = last;
      }

      @Override
      public boolean hasNext() {
         return first >= last;
      }

      @Override
      public Number next() {
         if(first >= last) {
            return first--;
         }
         return null;
      }
      
      @Override
      public void remove() {
         throw new UnsupportedOperationException("Illegal modification of range");
      }
   }
}
