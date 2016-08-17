package org.snapscript.core;

import java.util.concurrent.atomic.AtomicReferenceArray;

public class TypeCacheArray<V> {

   private final AtomicReferenceArray<V> array;
   private final int capacity;
   private final int expand;
   
   public TypeCacheArray(int capacity, int expand) {
      this.array = new AtomicReferenceArray<V>(capacity);
      this.capacity = capacity;
      this.expand = expand;
   }
   
   public TypeCacheArray copy(int require) {
      int length = array.length();
      
      if(require >= length) {
         TypeCacheArray copy = new TypeCacheArray(require + expand, expand);
         
         for(int i = 0; i < length; i++) {
            V value = array.get(i);
            
            if(value != null) {
               copy.set(i, value);
            }
         }
         return copy;
      }
      return this;
   }
   
   public V get(int index) {
      return array.get(index);
   }
   
   public V set(int index, V value) {
      return array.getAndSet(index, value);
   }

   public int length(){
      return capacity;
   }
}
