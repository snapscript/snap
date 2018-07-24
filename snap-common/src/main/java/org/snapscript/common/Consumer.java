package org.snapscript.common;

public interface Consumer<V, R> {
   R consume(V value);
}
