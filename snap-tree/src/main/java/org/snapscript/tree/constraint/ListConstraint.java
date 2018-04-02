package org.snapscript.tree.constraint;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.parse.StringToken;

public class ListConstraint extends Constraint {
   
   public ListConstraint(StringToken token) {
      super();
   }
   
   @Override
   public Type getType(Scope scope) {
      return LIST.getType(scope);
   }
}