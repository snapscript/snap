package org.snapscript.tree.constraint;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;

public interface Constraint {
   Type getType(Scope scope);
}