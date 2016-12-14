package org.snapscript.core.address;

import java.util.Iterator;

public class CompoundIterator<T> implements Iterator<T>{

   private final Iterator<T> first;
   private final Iterator<T> second;
   
   public CompoundIterator(Iterator<T> first, Iterator<T> second) {
      this.first = first;
      this.second = second;
   }

   @Override
   public boolean hasNext() {
      if(first.hasNext()){
         return true;
      }
      return second.hasNext();
   }

   @Override
   public T next() {
      if(first.hasNext()){
         return first.next();
      }
      return second.next();
   }
   
   @Override
   public void remove() {
      throw new UnsupportedOperationException("Remove not supported");
   }
}
