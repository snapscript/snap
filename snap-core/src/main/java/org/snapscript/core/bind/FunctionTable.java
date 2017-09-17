package org.snapscript.core.bind;

import org.snapscript.core.Type;

public interface FunctionTable {
   FunctionCall resolve(String name, Type... arguments) throws Exception;
   FunctionCall resolve(String name, Object... arguments) throws Exception;
   void update(FunctionCall call) throws Exception;
}
