package org.snapscript.core;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;

public class TypeCache<V> {

   private volatile TypeCacheArray<V> array;
   private volatile Cache<Type, V> cache;
   private volatile CacheUpdater updater;
   
   public TypeCache() {
      this(1000);
   }
   
   public TypeCache(int capacity) {
      this(capacity, 1000);
   }
   
   public TypeCache(int capacity, int expand) {
      this.array = new TypeCacheArray<V>(capacity, expand);
      this.cache = new CopyOnWriteCache<Type, V>();
      this.updater = new CacheUpdater(capacity);
   }

   public V take(Type type) {
      int order = type.getOrder();
      
      if(order == 0) {
         return cache.take(type);
      }
      int length = array.length();
      
      if(length > order) {
         return updater.take(order);
      }
      return null;
   }

   public V fetch(Type type) {
      int order = type.getOrder();
      
      if(order == 0) {
         return cache.fetch(type);
      }
      int length = array.length();
      
      if(length > order) {
         return array.get(order);
      }
      return null;
   }

   public boolean contains(Type type) {
      int order = type.getOrder();
      
      if(order == 0) {
         return cache.contains(type);
      }
      int length = array.length();
      
      if(length > order) {
         return array.get(order) != null;
      }
      return false;
   }

   public void cache(Type type, V value) {
      int order = type.getOrder();
      
      if(order == 0) {
         cache.cache(type, value);
      }
      int length = array.length();
      
      if(length > order) {
         array.set(order, value);
      } else {
         updater.cache(order, value);
      }
   }

   private class CacheUpdater {
      
      private final int expand;
      
      public CacheUpdater(int expand) {
         this.expand = expand;
      }
      
      public synchronized void cache(int order, V value) {
         int length = array.length();
         
         if(order >= length) {
            TypeCacheArray<V> copy = array.copy(order + expand);

            copy.set(order, value);
            array = copy;
         }
      }
      
      public synchronized V take(int order) {
         int length = array.length();
         
         if(length > order) {
            return array.set(order, null);
         }
         return null;
      }
   }
}
