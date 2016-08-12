package org.snapscript.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CopyOnWriteCache<K, V> implements Cache<K, V> {

   private volatile MapUpdater updater;
   private volatile Map<K, V> cache;
   
   public CopyOnWriteCache() {
      this.cache = new HashMap<K, V>();
      this.updater = new MapUpdater();
   }

   @Override
   public Set<K> keySet() {
      return cache.keySet();
   }

   @Override
   public V take(K key) {
      return cache.remove(key);
   }

   @Override
   public V fetch(K key) {
      return cache.get(key);
   }

   @Override
   public boolean isEmpty() {
      return cache.isEmpty();
   }

   @Override
   public boolean contains(K key) {
      return cache.containsKey(key);
   }
   
   @Override
   public void cache(K key, V value) {
      updater.cache(key, value);
   }

   @Override
   public void clear() {
      updater.clear();
   }

   @Override
   public int size() {
      return cache.size();
   }
   
   private class MapUpdater {
      
      private final Map<K, V> empty;
      private final Lock lock;
      
      public MapUpdater() {
         this.empty = new HashMap<K, V>();
         this.lock = new ReentrantLock();
      }
      
      public void cache(K key, V value) {
         lock.lock();
         
         try {
            Map<K, V> copy = new HashMap<K, V>(cache);
            
            copy.put(key, value);
            cache = copy;
         } finally {
            lock.unlock();
         }
      }
      
      public void clear() {
         lock.lock();
         
         try {
            cache = empty;
         } finally {
            lock.unlock();
         }
      }
   }
}
