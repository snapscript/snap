package org.snapscript.tree.variable;

import static org.snapscript.core.Value.NULL;
import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.constraint.Constraint;

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
