package org.snapscript.core.bind;

import org.snapscript.core.Type;
import org.snapscript.core.function.Function;

public interface FunctionGroup {
   Function resolve(String name, Type... list) throws Exception;
   Function resolve(String name, Object... list) throws Exception;
   void update(Function function) throws Exception;
}
