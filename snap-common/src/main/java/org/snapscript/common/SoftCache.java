package org.snapscript.common;

import java.lang.ref.SoftReference;
import java.util.Set;

public class SoftCache<K, V> implements Cache<K, V> {
   
   private final Cache<K, SoftReference<V>> cache;

   public SoftCache() {
      this(100);
   }
   
   public SoftCache(int capacity) {
      this.cache = new LeastRecentlyUsedCache<K, SoftReference<V>>(capacity);
   }

   @Override
   public Set<K> keySet() {
      return cache.keySet();
   }

   @Override
   public V take(K key) {
      SoftReference<V> reference = cache.take(key);
      
      if(reference != null) {
         return reference.get();
      }
      return null;
   }

   @Override
   public V fetch(K key) {
      SoftReference<V> reference = cache.fetch(key);
      
      if(reference != null) {
         return reference.get();
      }
      return null;
   }
   
   @Override
   public V fetch(K key, ValueBuilder<K, V> builder) {
      SoftReference<V> reference = cache.fetch(key);
      
      if(reference == null) {         
         V value = builder.create(key);
         
         if(value != null) {
            cache(key, value);
         }
         return value;
      }
      return reference.get();
   } 
   
   @Override
   public void cache(K key, V value) {
      SoftReference<V> reference = new SoftReference<V>(value);
      
      if(value != null) {
         cache.cache(key, reference);
      }
   }
   
   @Override
   public boolean contains(K key) {
      SoftReference<V> reference = cache.fetch(key);
      
      if(reference != null) {
         return reference.get() != null;
      }
      return false;
   }

   @Override
   public boolean isEmpty() {
      return cache.isEmpty();
   }


   @Override
   public void clear() {
      cache.clear();
   }

   @Override
   public int size() {
      return cache.size();
   }
   
   @Override
   public String toString() {
      return String.valueOf(cache);
   }
}