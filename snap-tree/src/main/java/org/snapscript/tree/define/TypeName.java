package org.snapscript.tree.define;

import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;

public interface TypeName {
   String getName(Scope scope) throws Exception;
   List<Constraint> getGenerics(Scope scope) throws Exception;
}