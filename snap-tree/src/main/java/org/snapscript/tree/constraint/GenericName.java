package org.snapscript.tree.constraint;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;

import java.util.List;

public interface GenericName {
   String getName(Scope scope) throws Exception;
   List<Constraint> getGenerics(Scope scope) throws Exception;
}
