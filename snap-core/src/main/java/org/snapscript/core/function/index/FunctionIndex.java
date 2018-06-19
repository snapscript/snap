package org.snapscript.core.function.index;

import java.util.List;

import org.snapscript.core.type.Type;

public interface FunctionIndex {
   List<FunctionPointer> resolve(int modifiers) throws Exception;
   FunctionPointer resolve(String name, Type... arguments) throws Exception;
   FunctionPointer resolve(String name, Object... arguments) throws Exception;
   void index(FunctionPointer pointer) throws Exception;
}