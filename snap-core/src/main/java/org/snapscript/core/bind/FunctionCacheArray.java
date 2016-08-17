package org.snapscript.core.bind;

import java.util.concurrent.atomic.AtomicReferenceArray;

public class FunctionCacheArray {
   
   private final AtomicReferenceArray<FunctionCache> array;
   private final int capacity;
   private final int expand;
   
   public FunctionCacheArray(int capacity, int expand) {
      this.array = new AtomicReferenceArray<FunctionCache>(capacity);
      this.capacity = capacity;
      this.expand = expand;
   }
   
   public FunctionCacheArray copy(int require) {
      int length = array.length();
      
      if(require >= length) {
         FunctionCacheArray copy = new FunctionCacheArray(require + expand, expand);
         
         for(int i = 0; i < length; i++) {
            FunctionCache cache = array.get(i);
            copy.set(i, cache);
         }
         return copy;
      }
      return this;
   }
   
   public FunctionCache get(int index) {
      return array.get(index);
   }
   
   public void set(int index, FunctionCache cache) {
      array.set(index, cache);
   }

   public int length(){
      return capacity;
   }
}
