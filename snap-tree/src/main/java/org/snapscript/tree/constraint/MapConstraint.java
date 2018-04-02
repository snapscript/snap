package org.snapscript.tree.constraint;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.parse.StringToken;

public class MapConstraint extends Constraint {
   
   public MapConstraint(StringToken token) {
      super();
   }
   
   @Override
   public Type getType(Scope scope) {
      return MAP.getType(scope);
   }
}