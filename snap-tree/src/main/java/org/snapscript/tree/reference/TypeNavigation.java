package org.snapscript.tree.reference;

import static org.snapscript.core.constraint.Constraint.NONE;
import static org.snapscript.core.variable.Value.NULL;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public abstract class TypeNavigation extends Evaluation {
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Value value = evaluate(scope, NULL);
      
      if(value != null) {
         return value.getConstraint();
      }
      return NONE;
   }
   
   public abstract String qualify(Scope scope, String left) throws Exception;
}
