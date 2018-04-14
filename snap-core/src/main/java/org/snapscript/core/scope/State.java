package org.snapscript.core.scope;

import org.snapscript.core.variable.Value;

public interface State extends Iterable<String> {
   Value get(String name);
   void add(String name, Value value);
}