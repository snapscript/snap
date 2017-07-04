package org.snapscript.core.bind;

import org.snapscript.core.Type;
import org.snapscript.core.function.Function;

public interface FunctionResolver {
   Function resolve(Type type, String name, Object... values) throws Exception;
}
