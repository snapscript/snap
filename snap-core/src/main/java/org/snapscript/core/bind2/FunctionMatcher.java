package org.snapscript.core.bind2;

import java.util.List;

import org.snapscript.core.Type;
import org.snapscript.core.function.Function;

public interface FunctionMatcher {
   Function match(List<Function> functions, Type... types) throws Exception;
   Function match(List<Function> functions, Object... list) throws Exception;
}
