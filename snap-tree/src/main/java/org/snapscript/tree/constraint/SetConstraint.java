package org.snapscript.tree.constraint;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.parse.StringToken;

public class SetConstraint extends Constraint {
   
   public SetConstraint(StringToken token) {
      super();
   }
   
   @Override
   public Type getType(Scope scope) {
      return SET.getType(scope);
   }
}