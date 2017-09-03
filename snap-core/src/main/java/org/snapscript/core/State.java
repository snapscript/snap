package org.snapscript.core;

import java.util.List;
import java.util.Set;

public interface State extends Iterable<String> {
   Value getScope(String name);
   void addScope(String name, Value value);
   Value getLocal(int index);
   void addLocal(int index, Value value);
   int getLocal(String name);
   int addLocal(String name);
   Set<String> getLocals();
   List<Value> getStack();
   int getDepth();
}