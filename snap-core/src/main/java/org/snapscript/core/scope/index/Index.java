package org.snapscript.core.scope.index;

public interface Index extends Iterable<String> {
   int get(String name);
   int index(String name);
   void reset(int index);
   int size();
}