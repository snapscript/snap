package org.snapscript.tree.variable;

import static org.snapscript.core.constraint.Constraint.NONE;
import static org.snapscript.core.scope.Value.NULL;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.Value;

public class EmptyPointer implements VariablePointer {
   
   public EmptyPointer() {
      super();
   }

   @Override
   public Constraint check(Scope scope, Constraint left) {
      return NONE;
   }

   @Override
   public Value get(Scope scope, Object left) {
      return NULL;
   }
   

}
