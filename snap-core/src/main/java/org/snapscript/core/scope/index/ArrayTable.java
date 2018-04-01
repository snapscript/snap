package org.snapscript.core.scope.index;

import java.util.Iterator;

public class ArrayTable implements Table {
   
   private Local[] table;

   public ArrayTable() {
      this(0);
   }
   
   public ArrayTable(int count) {
      this.table = new Local[count];
   }

   @Override
   public Iterator<Local> iterator() {
      return new LocalIterator(table);
   }

   @Override
   public Local get(int index) {
      if(index < table.length && index >= 0) {
         return table[index];
      }
      return null;
   }
   
   @Override
   public void add(int index, Local local) {
      if(local == null) {
         throw new IllegalStateException("Local at index " + index + " is null");
      }
      if(index >= table.length) {
         Local[] copy = new Local[index == 0 ? 2 : index * 2];
         
         for(int i = 0; i < table.length; i++) {
            copy[i] = table[i];
         }
         table = copy;
      }
      table[index] = local;
   }
   
   private static class LocalIterator implements Iterator<Local> {
      
      private Local[] table;
      private Local local;
      private int index;

      public LocalIterator(Local[] table) {
         this.table = table;
      }
      
      @Override
      public boolean hasNext() {
         while(local == null) {
            if(index >= table.length) {
               break;
            }
            local = table[index++];
         }
         return local != null;
      }

      @Override
      public Local next() {
         Local next = null;
         
         if(hasNext()) {
            next = local;
            local = null;
         }
         return next;
      }
   }
}
