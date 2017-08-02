package org.snapscript.core.bind;

public class FunctionCacheTable<T> {

   private volatile FunctionCacheIndexer<T> indexer;
   private volatile FunctionCacheArray array;
   private volatile CacheAllocator allocator;
   
   public FunctionCacheTable(FunctionCacheIndexer<T> indexer) {
      this(indexer, 5000);
   }
   
   public FunctionCacheTable(FunctionCacheIndexer<T> indexer, int capacity) {
      this.array = new FunctionCacheArray(capacity, capacity);
      this.allocator = new CacheAllocator(indexer);
      this.indexer = indexer;
   }
   
   public FunctionCache get(T value) {
      int index = indexer.index(value); // index 0 will cluster anonymous types
      int length = array.length();
      
      if(index >= length) {
         return allocator.allocate(value);
      }
      FunctionCache cache = array.get(index);
      
      if(cache == null) {
         return allocator.allocate(value);
      }
      return cache;
   }
   
   private class CacheAllocator {
      
      private final FunctionCacheIndexer<T> indexer;
      
      public CacheAllocator(FunctionCacheIndexer<T> indexer) {
         this.indexer = indexer;
      }
      
      public synchronized FunctionCache allocate(T value) {
         int index = indexer.index(value);
         FunctionCacheArray local = array.copy(index);
         FunctionCache cache = local.get(index);
         
         if(cache == null) {
            cache = new FunctionCache();
            local.set(index, cache);
            array = local;
         }
         return cache;
      }
   }
}