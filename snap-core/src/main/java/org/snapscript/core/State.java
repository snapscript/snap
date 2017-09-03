package org.snapscript.core;

import java.util.List;
import java.util.Set;

public interface State extends Iterable<String> {
   Value getScope(String name);
   void addScope(String name, Value value);
   Local getLocal(int index);
   void addLocal(int index, Local local);
   int getLocal(String name);
   int addLocal(String name);
   Set<String> getLocals();
   List<Local> getStack();
   int getDepth();
}