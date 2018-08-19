package org.snapscript.tree.constraint;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;

public interface GenericList {
   List<Constraint> getGenerics(Scope scope) throws Exception;
}
