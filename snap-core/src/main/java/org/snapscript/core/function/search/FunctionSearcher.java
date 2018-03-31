package org.snapscript.core.function.search;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.convert.Delegate;

public interface FunctionSearcher {
   FunctionCall searchValue(Value value, Object... list) throws Exception;
   FunctionCall searchScope(Scope scope, String name, Type... list) throws Exception;
   FunctionCall searchScope(Scope scope, String name, Object... list) throws Exception;
   FunctionCall searchModule(Scope scope, Module module, String name, Type... list) throws Exception;
   FunctionCall searchModule(Scope scope, Module module, String name, Object... list) throws Exception;
   FunctionCall searchStatic(Scope scope, Type type, String name, Type... list) throws Exception;
   FunctionCall searchStatic(Scope scope, Type type, String name, Object... list) throws Exception;
   FunctionCall searchFunction(Scope scope, Type delegate, String name, Type... list) throws Exception;
   FunctionCall searchFunction(Scope scope, Delegate delegate, String name, Object... list) throws Exception;
   FunctionCall searchInstance(Scope scope, Type source, String name, Type... list) throws Exception;
   FunctionCall searchInstance(Scope scope, Object source, String name, Object... list) throws Exception;
}