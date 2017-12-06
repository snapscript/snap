package org.snapscript.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashCache<K, V> implements Cache<K, V> {
   
   private volatile Map<K, V> map;

   public HashCache() {
      super();
   }

   @Override
   public Set<K> keySet() {
      if(map != null) {
         return map.keySet();
      }
      return Collections.emptySet();
   }

   @Override
   public V take(K key) {
      if(map != null) {
         return map.remove(key);
      }
      return null;
   }

   @Override
   public V fetch(K key) {
      if(map != null) {
         return map.get(key);
      }
      return null;
   }

   @Override
   public boolean isEmpty() {
      if(map != null) {
         return map.isEmpty();
      }
      return true;
   }

   @Override
   public boolean contains(K key) {
      if(map != null) {
         return map.containsKey(key);
      }
      return false;
   }

   @Override
   public void cache(K key, V value) {
      if(map == null) {
         map = new HashMap<K, V>();
      }
      map.put(key, value);
   }

   @Override
   public void clear() {
      if(map != null) {
         map.clear();
      }
   }

   @Override
   public int size() {
      if(map != null) {
         return map.size();
      }
      return 0;
   }
   
   @Override
   public String toString() {
      return String.valueOf(map);
   }
}
