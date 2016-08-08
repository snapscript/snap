package org.snapscript.core.bind;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.snapscript.core.function.Function;

public class FunctionCache { // copy on write cache

   private volatile Map<Object, Function> cache;
   private volatile CacheUpdater updater; 
   
   public FunctionCache() {
      this.cache = new HashMap<Object, Function>();
      this.updater = new CacheUpdater();
   }
   
   public boolean contains(Object key) {
      return cache.containsKey(key);
   }
   
   public Function fetch(Object key) {
      return cache.get(key);
   }
   
   public void cache(Object key, Function function) {
      updater.update(key, function);
   }
   
   private class CacheUpdater {
      
      private final Lock lock;
      
      public CacheUpdater() {
         this.lock = new ReentrantLock();
      }
      
      public void update(Object key, Function function) {
         lock.lock();
         
         try {
            Map<Object, Function> local = new HashMap<Object, Function>(cache);
            
            local.put(key, function);
            cache = local;
         } finally {
            lock.unlock();
         }
      }
   }
}
