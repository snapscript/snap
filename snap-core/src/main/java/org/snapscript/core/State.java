package org.snapscript.core;

import java.util.List;

public interface State extends Iterable<String> {
   Value getScope(String name);
   void addScope(String name, Value value);
   Local getLocal(int index);
   void addLocal(int index, Local local);
   List<Local> getStack();
}