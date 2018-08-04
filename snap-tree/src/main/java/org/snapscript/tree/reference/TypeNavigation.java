package org.snapscript.tree.reference;

import static org.snapscript.core.variable.Value.NULL;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;

public abstract class TypeNavigation extends Evaluation {
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      return evaluate(scope, NULL);
   }
   
   public abstract String qualify(Scope scope, String left) throws Exception;
}
