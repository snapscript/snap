package org.snapscript.core.bind;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.function.Function;

public class FunctionCache {

   private final Cache<Object, Function> cache;
   
   public FunctionCache() {
      this.cache = new CopyOnWriteCache<Object, Function>();
   }
   
   public boolean contains(Object key) {
      return cache.contains(key);
   }
   
   public Function fetch(Object key) {
      return cache.fetch(key);
   }
   
   public void cache(Object key, Function function) {
      cache.cache(key, function);
   }
}
