package org.snapscript.core.scope.index;

public interface Index extends Iterable<Address> {
   Address get(String name);
   Address index(String name);
   boolean contains(String name);
   void reset(int index);
   int size();
}