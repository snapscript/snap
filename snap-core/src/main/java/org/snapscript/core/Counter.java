package org.snapscript.core;

public interface Counter extends Iterable<String> {
   int get(String name);
   int add(String name);
   int size();
}