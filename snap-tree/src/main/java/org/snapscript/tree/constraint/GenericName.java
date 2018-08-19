package org.snapscript.tree.constraint;

import org.snapscript.core.scope.Scope;

public interface GenericName extends GenericList {
   String getName(Scope scope) throws Exception;
}
