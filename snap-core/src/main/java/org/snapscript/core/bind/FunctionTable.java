package org.snapscript.core.bind;

import org.snapscript.core.Type;
import org.snapscript.core.function.Function;

public interface FunctionTable {
   Function resolve(String name, Type... arguments) throws Exception;
   Function resolve(String name, Object... arguments) throws Exception;
   void update(Function function) throws Exception;
}
