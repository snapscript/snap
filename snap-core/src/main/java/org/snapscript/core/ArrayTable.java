package org.snapscript.core;

import java.util.Iterator;

public class ArrayTable implements Table {
   
   private Local[] table;

   public ArrayTable() {
      this(2);
   }
   
   public ArrayTable(int count) {
      this.table = new Local[count];
   }

   @Override
   public Iterator<Local> iterator() {
      return new LocalIterator(table);
   }

   public Local get(int index) {
      if(index >= table.length) {
         throw new InternalStateException("Local index " + index + " exceeds depth " + table.length);
      }
      return table[index];
   }
   
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
         while(index < table.length) {
            local = table[index++];
            
            if(local != null) {
               return true;
            }
         }
         return false;
      }

      @Override
      public Local next() {
         return local;
      }
   }
}
