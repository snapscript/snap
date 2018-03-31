package org.snapscript.core.function.search;

import org.snapscript.core.Type;

public interface FunctionIndex {
   FunctionPointer resolve(String name, Type... arguments) throws Exception;
   FunctionPointer resolve(String name, Object... arguments) throws Exception;
   void index(FunctionPointer pointer) throws Exception;
}