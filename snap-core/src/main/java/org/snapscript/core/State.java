package org.snapscript.core;

public interface State extends Iterable<String> {
   Value getScope(String name);
   void addScope(String name, Value value);
}