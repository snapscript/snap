package org.snapscript.tree.collection;

import java.util.Iterator;

import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Data;
import org.snapscript.core.variable.ValueCache;

public class Sequence implements Iterable<Data> {

   private final Module module;
   private final long first;
   private final long last;
   
   public Sequence(Module module, long first, long last) {
      this.module = module;
      this.first = first;
      this.last = last;
   }
   
   @Override
   public Iterator<Data> iterator() {
      Scope scope = module.getScope();
      
      if(first > last) {
         return new ReverseIterator(scope, first, last);
      }
      return new ForwardIterator(scope, first, last);
   }

   private static class ForwardIterator implements Iterator<Data> {
      
      private Scope scope;
      private long first;
      private long last;
      
      public ForwardIterator(Scope scope, Long first, Long last) {
         this.scope = scope;
         this.first = first;
         this.last = last;
      }

      @Override
      public boolean hasNext() {
         return first <= last;
      }

      @Override
      public Data next() {
         if(first <= last) {
            return ValueCache.getLong(scope, first++);
         }
         return null;
      }
      
      @Override
      public void remove() {
         throw new UnsupportedOperationException("Illegal modification of range");
      }
   }

   private static class ReverseIterator implements Iterator<Data> {
      
      private Scope scope;
      private long first;
      private long last;
      
      public ReverseIterator(Scope scope, long first, long last) {
         this.scope = scope;
         this.first = first;
         this.last = last;
      }

      @Override
      public boolean hasNext() {
         return first >= last;
      }

      @Override
      public Data next() {
         if(first >= last) {
            return ValueCache.getLong(scope, first--);
         }
         return null;
      }
      
      @Override
      public void remove() {
         throw new UnsupportedOperationException("Illegal modification of range");
      }
   }
}