package org.snapscript.core;

import java.util.Set;

public interface State {
   Set<String> getNames();
   Value getValue(String name);
   void setValue(String name, Value value);
   void addVariable(String name, Value value);
   void addConstant(String name, Value value);
}
