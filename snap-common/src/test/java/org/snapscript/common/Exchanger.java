package org.snapscript.common;

public interface Exchanger<K, V> {
   V get(K key);
   void set(K key, V value);
}
