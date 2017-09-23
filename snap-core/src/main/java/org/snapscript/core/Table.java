package org.snapscript.core;

public interface Table extends Iterable<Local> {
   Local get(int index);
   void add(int index, Local local);
}