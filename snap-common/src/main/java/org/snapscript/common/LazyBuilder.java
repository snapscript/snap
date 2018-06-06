package org.snapscript.common;

public interface LazyBuilder<K, V> {
   V create(K key);
}
