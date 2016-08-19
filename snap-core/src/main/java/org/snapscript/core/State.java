package org.snapscript.core;

import java.util.Set;

public interface State {
   Set<String> getNames();
   Value getValue(String name);
   void setValue(String name, Value value);
   void addValue(String name, Value value);
}
