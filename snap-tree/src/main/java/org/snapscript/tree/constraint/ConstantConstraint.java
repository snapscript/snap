package org.snapscript.tree.constraint;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;

public class ConstantConstraint implements Constraint {
   
   private final Type type;
   
   public ConstantConstraint(Type type) {
      this.type = type;
   }

   @Override
   public Type getType(Scope scope) {
      return type;
   }

}
