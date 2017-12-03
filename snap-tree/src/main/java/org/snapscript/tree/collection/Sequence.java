package org.snapscript.tree.collection;

import java.util.Iterator;

public class Sequence implements Iterable<Number> {

   private final long first;
   private final long last;
   
   public Sequence(long first, long last) {
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
      
      public ReverseIterator(long first, long last) {
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