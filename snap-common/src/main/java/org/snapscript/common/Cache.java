package org.snapscript.common;

import java.util.Set;

public interface Cache<K, V> {
   Set<K> keySet();
   V take(K key);
   V fetch(K key);
   V fetch(K key, ValueBuilder<K, V> builder);   
   boolean isEmpty();
   boolean contains(K key);
   void cache(K key, V value);
   void clear();
   int size();
}