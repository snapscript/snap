package org.snapscript.core;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.common.CopyOnWriteSparseArray;
import org.snapscript.common.SparseArray;

public class TypeCache<V> {

   private final SparseArray<V> array;
   private final Cache<Type, V> cache;
   
   public TypeCache() {
      this(1000);
   }
   
   public TypeCache(int capacity) {
      this.array = new CopyOnWriteSparseArray<V>(capacity); // for order > 0
      this.cache = new CopyOnWriteCache<Type, V>(); // for order = 0
   }

   public V take(Type type) {
      int order = type.getOrder();
      
      if(order == 0) {
         return cache.take(type);
      }
      return array.remove(order);
   }

   public V fetch(Type type) {
      int order = type.getOrder();
      
      if(order == 0) {
         return cache.fetch(type);
      }
      return array.get(order);
   }

   public boolean contains(Type type) {
      int order = type.getOrder();
      
      if(order == 0) {
         return cache.contains(type);
      }
      return array.get(order) != null;
   }

   public void cache(Type type, V value) {
      int order = type.getOrder();
      
      if(order == 0) {
         cache.cache(type, value);
      }
      array.set(order, value);
   }
}