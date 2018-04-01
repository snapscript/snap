package org.snapscript.core.scope;

public interface State extends Iterable<String> {
   Value get(String name);
   void add(String name, Value value);
}