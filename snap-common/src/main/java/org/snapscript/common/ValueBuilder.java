package org.snapscript.common;

public interface ValueBuilder<K, V> {
   V create(K key);
}
