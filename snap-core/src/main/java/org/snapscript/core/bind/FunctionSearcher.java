package org.snapscript.core.bind;

import java.util.List;

import org.snapscript.core.Type;
import org.snapscript.core.function.Function;

public interface FunctionSearcher {
   Function search(List<Function> functions, Type... types) throws Exception;
   Function search(List<Function> functions, Object... list) throws Exception;
}
