package org.snapscript.core.function.index;

import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;

public interface FunctionPointer {
   Function getFunction();
   Invocation getInvocation();   
}