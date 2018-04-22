package org.snapscript.tree.define;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public interface TypeHierarchy {
   void define(Scope scope, Type type) throws Exception;
   void compile(Scope scope, Type type) throws Exception;
}