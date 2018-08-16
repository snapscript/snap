package org.snapscript.tree.define;

import org.snapscript.core.scope.Scope;
import org.snapscript.tree.constraint.GenericName;

public interface TypeName extends GenericName {
   int getModifiers(Scope scope) throws Exception;
}