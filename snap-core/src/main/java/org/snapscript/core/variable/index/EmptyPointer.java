package org.snapscript.core.variable.index;

import static org.snapscript.core.constraint.Constraint.NONE;
import static org.snapscript.core.variable.Value.NULL;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public class EmptyPointer implements VariablePointer {
   
   public EmptyPointer() {
      super();
   }

   @Override
   public Constraint compile(Scope scope, Constraint left) {
      return NONE;
   }

   @Override
   public Value get(Scope scope, Object left) {
      return NULL;
   }
   

}
