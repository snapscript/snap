package org.snapscript.tree.collection;

import java.util.Iterator;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
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
            throw new InternalStateException("Range " + first + ".." + last + " is invalid");
         }
         return new RangeIterator(first, last);
      }
   }
   
   private static class RangeIterator implements Iterator<Number> {
      
      private long first;
      private long last;
      
      public RangeIterator(Long first, Long last) {
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
}
