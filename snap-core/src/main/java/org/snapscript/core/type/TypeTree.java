package org.snapscript.core.type;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;

public class TypeTree<K, V>  {

   private final TypeCache<Cache<K, V>> cache;
   
   public TypeTree() {
      this.cache = new TypeCache<Cache<K, V>>();
   }
   
   public Cache<K, V> get(Type type) {
      Cache<K, V> table = cache.fetch(type);
      
      if(table == null) {
         table = new CopyOnWriteCache<K, V>();
         cache.cache(type, table);
      }
      return table;
      
   }
}
