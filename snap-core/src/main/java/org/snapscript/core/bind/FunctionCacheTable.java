package org.snapscript.core.bind;

import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FunctionCacheTable<T> {

   private volatile FunctionCacheIndexer<T> indexer;
   private volatile CacheAllocator allocator;
   private volatile CacheArray array;
   
   public FunctionCacheTable(FunctionCacheIndexer<T> indexer) {
      this(indexer, 5000);
   }
   
   public FunctionCacheTable(FunctionCacheIndexer<T> indexer, int capacity) {
      this.array = new CacheArray(capacity, capacity);
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
      private final Lock lock;
      
      public CacheAllocator(FunctionCacheIndexer<T> indexer) {
         this.lock = new ReentrantLock();
         this.indexer = indexer;
      }
      
      public FunctionCache allocate(T value) {
         lock.lock();
         
         try {
            int index = indexer.index(value);
            CacheArray local = array.copy(index);
            FunctionCache cache = local.get(index);
            
            if(cache == null) {
               cache = new FunctionCache();
               local.set(index, cache);
               array = local;
            }
            return cache;
         } finally {
            lock.unlock();
         }
      }
   }
   
   private class CacheArray {
      
      private final AtomicReferenceArray<FunctionCache> array;
      private final int capacity;
      private final int expand;
      
      public CacheArray(int capacity, int expand) {
         this.array = new AtomicReferenceArray<FunctionCache>(capacity);
         this.capacity = capacity;
         this.expand = expand;
      }
      
      public FunctionCache get(int index) {
         return array.get(index);
      }
      
      public void set(int index, FunctionCache cache) {
         array.set(index, cache);
      }
      
      public CacheArray copy(int require) {
         int length = array.length();
         
         if(require >= length) {
            CacheArray copy = new CacheArray(require + expand, expand);
            
            for(int i = 0; i < length; i++) {
               FunctionCache cache = array.get(i);
               copy.set(i, cache);
            }
            return copy;
         }
         return this;
      }
      
      public int length(){
         return capacity;
      }
   }
}
